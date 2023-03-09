/*
 * Copyright 2018 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.service;

import com.ecyrd.speed4j.StopWatch;
import com.ecyrd.speed4j.StopWatchFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.russianpost.authorizationservice.AuthorizationFault;
import ru.russianpost.authorizationservice.AuthorizationService;
import ru.russianpost.authorizationservice.AuthorizationServicePortType;
import ru.russianpost.authorizationservice.data.GetTicketRequest;
import ru.russianpost.authorizationservice.data.GetTicketResponse;
import ru.russianpost.esb.exchange.s0.PutDataFaultMessage;
import ru.russianpost.esb.exchange.s0.S0PortType;
import ru.russianpost.esb.exchange.s0.S0Service;
import ru.russianpost.esb.exchange.s0.xsd.PutDataFaultType;
import ru.russianpost.esb.exchange.s0.xsd.PutDataRequest;
import ru.russianpost.esb.exchange.s0.xsd.PutDataResponse;
import ru.russianpost.tracking.portal.admin.ConfigHolder;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.xml.ws.BindingProvider;
import java.net.MalformedURLException;
import java.net.URL;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.nonNull;

/**
 * SOAP web service client, responsible for communicating with the OASU RPO RTM-30 web services.
 * @author mkitchenko
 * @version 1.0 14.03.2016
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OasuRpoRtm30Client {

    private static final ru.russianpost.authorizationservice.data.ObjectFactory AUTH_OBJECT_FACTORY =
        new ru.russianpost.authorizationservice.data.ObjectFactory();
    private static final ru.russianpost.esb.exchange.s0.xsd.ObjectFactory DATA_OBJECT_FACTORY =
        new ru.russianpost.esb.exchange.s0.xsd.ObjectFactory();

    /**
     * Enable/disable client normal workflow.
     */
    private static final boolean USE_RTM_30 = ConfigHolder.CONFIG.getBoolean("rtm30.enable");
    /**
     * Login for authorization service.
     */
    private static final String LOGIN = ConfigHolder.CONFIG.getString("rtm30.oasu.rpo.login");
    /**
     * Password for authorization service.
     */
    private static final String PASSWORD = ConfigHolder.CONFIG.getString("rtm30.oasu.rpo.password");
    /**
     * Software id for authorization service.
     */
    private static final int SOFTWARE_ID = ConfigHolder.CONFIG.getInt("rtm30.oasu.rpo.softwareId");
    /**
     * OASU RPO services connect/request timeout.
     */
    private static final int TIMEOUT = ConfigHolder.CONFIG.getInt("rtm30.oasu.rpo.timeout");
    /**
     * If true, then in case of RTM_30_IS_NOT_VALID error from Niips, rtm-30 xml will be logged.
     */
    private static final boolean LOG_INVALID_XML = ConfigHolder.CONFIG.getBoolean("rtm30.log.invalid.xml");

    @Value("${oasu.rpo.auth.service.url}")
    private final String authServiceUrl;
    @Value("${oasu.rpo.data.service.url}")
    private final String dataServiceUrl;
    private final StopWatchFactory stopWatchFactory;

    private volatile AuthorizationServicePortType authServicePort;
    private volatile S0PortType dataServicePort;
    private volatile String storedTicket;
    private volatile boolean ticketExpired;

    private boolean initialized = false;

    /**
     * Initializes the OASU RPO web client.
     */
    @PostConstruct
    public void init() {

        if (!USE_RTM_30) {
            log.info("OASU RPO Web client initialization disabled.");
            initialized = false;
            return;
        }

        log.info(
            "OASU RPO Web client initialization: authServiceUrl = \"{}\", dataServiceUrl = \"{}\".",
            authServiceUrl,
            dataServiceUrl
        );
        if (dataServicePort == null) {
            synchronized (this) {
                if (dataServicePort == null) {
                    final URL authServiceWsdlLocation;
                    final URL dataServiceWsdlLocation;
                    try {
                        authServiceWsdlLocation = new URL(authServiceUrl + "?wsdl");
                        dataServiceWsdlLocation = new URL(dataServiceUrl + "?wsdl");
                    } catch (MalformedURLException e) {
                        log.error("Cannot init client: malformed URLs.", e);
                        initialized = false;
                        return;
                    }

                    try {
                        final AuthorizationService authService = new AuthorizationService(authServiceWsdlLocation);
                        authServicePort = authService.getAuthorizationServicePort();
                        final BindingProvider authServiceBp = (BindingProvider) authServicePort;
                        authServiceBp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, authServiceUrl);
                        authServiceBp.getRequestContext().put("com.sun.xml.internal.ws.connect.timeout", TIMEOUT);
                        authServiceBp.getRequestContext().put("com.sun.xml.internal.ws.request.timeout", TIMEOUT);

                        final S0Service dataService = new S0Service(dataServiceWsdlLocation);
                        dataServicePort = dataService.getS0ServicePort();
                        final BindingProvider dataServiceBp = (BindingProvider) dataServicePort;
                        dataServiceBp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, dataServiceUrl);
                        dataServiceBp.getRequestContext().put("com.sun.xml.internal.ws.connect.timeout", TIMEOUT);
                        dataServiceBp.getRequestContext().put("com.sun.xml.internal.ws.request.timeout", TIMEOUT);

                        storedTicket = null;
                        ticketExpired = false;

                        initialized = true;
                        log.info("Successfully initialized client.");
                    } catch (Exception e) {
                        log.error("Cannot init client: error while creating connection.", e);
                        initialized = false;
                    }
                }
            }
        } else {
            throw new IllegalStateException("OASU RPO web client is already available. Terminate old client first.");
        }
    }

    /**
     * Sends RTM-30 data to the OASU RPO data service.<br>
     * Asks OASU RPO authorization service for new auth ticket, if it's first request or if old ticket is expired.
     * <p>
     * Note: in case of TICKET_EXPIRED and DUPLICATE_MESSAGE errors, not Exception will be thrown.
     * Instead, error message will be logged.
     * @param rtm30Xml XML string in RTM-30 format.
     * @throws Exception in case of "Authorization Fault" or "Put Data Fault" message from OASU RPO.
     */
    public void sendRtm30Data(final String rtm30Xml) throws Exception {
        final StopWatch stopWatch = stopWatchFactory.getStopWatch("sendRtm30ToNiips:requestSoap");

        try {
            if (!initialized) {
                throw new IllegalStateException("Client is not initialized.");
            }

            log.debug("Sending RTM-30 data.");
            updateTicket();
            final byte[] rtm30Data = rtm30Xml.getBytes(UTF_8);

            boolean ticketExpiredWasCaught = false;
            PutDataRequest putDataRequest = constructPutDataRequest(storedTicket, rtm30Data);
            PutDataResponse putDataResponse = null;
            try {
                putDataResponse = dataServicePort.putData(putDataRequest);
            } catch (PutDataFaultMessage putDataFaultMessage) {
                if (PutDataFaultType.TICKET_EXPIRED == putDataFaultMessage.getFaultInfo().getCode()) {
                    ticketExpiredWasCaught = true;
                } else {
                    processPutDataFaultMessage(rtm30Xml, putDataFaultMessage);
                }
            }

            if (ticketExpiredWasCaught) {
                ticketExpired = true;
                updateTicket();
                putDataRequest = constructPutDataRequest(storedTicket, rtm30Data);
                try {
                    putDataResponse = dataServicePort.putData(putDataRequest);
                } catch (PutDataFaultMessage putDataFaultMessage) {
                    processPutDataFaultMessage(rtm30Xml, putDataFaultMessage);
                }
            }

            if (nonNull(putDataResponse)) {
                log.debug("RTM-30 data has been successfully sent.");
            }
        } finally {
            stopWatch.stop();
        }
    }

    private void updateTicket() throws Exception {

        if (storedTicket == null || ticketExpired) {
            synchronized (this) {
                if (storedTicket == null || ticketExpired) {
                    storedTicket = askTicket();
                    ticketExpired = false;
                }
            }
        }
    }

    private String askTicket() throws Exception {

        log.info("Asking new ticket.");
        final GetTicketRequest getTicketRequest = constructGetTicketRequest();
        final GetTicketResponse getTicketResponse;
        try {
            getTicketResponse = authServicePort.getTicket(getTicketRequest);
        } catch (AuthorizationFault authorizationFault) {
            final String authorizationFaultCode = authorizationFault.getFaultInfo().getCode().value();
            final String authorizationFaultDescription = authorizationFault.getFaultInfo().getDescription();
            final String message = "Cannot get ticket: " + authorizationFaultCode + " - " + authorizationFaultDescription;
            throw new Exception(message, authorizationFault);
        }

        final String result = getTicketResponse.getTicket();
        log.info("New ticket: \"{}\".", result);

        if (result == null || result.isEmpty()) {
            throw new Exception("Empty ticket.");
        } else {
            return result;
        }
    }

    private GetTicketRequest constructGetTicketRequest() {

        final GetTicketRequest getTicketRequest = AUTH_OBJECT_FACTORY.createGetTicketRequest();

        getTicketRequest.setLogin(LOGIN);
        getTicketRequest.setPassword(PASSWORD);
        getTicketRequest.setSoftwareId(SOFTWARE_ID);

        return getTicketRequest;
    }

    private PutDataRequest constructPutDataRequest(final String ticket, final byte[] rtm30Data) {

        final PutDataRequest putDataRequest = DATA_OBJECT_FACTORY.createPutDataRequest();

        putDataRequest.setTicket(ticket);
        putDataRequest.setData(rtm30Data);

        return putDataRequest;
    }

    private void processPutDataFaultMessage(String rtm30Xml, PutDataFaultMessage putDataFaultMessage) throws Exception {
        final String represent = represent(putDataFaultMessage);
        if (PutDataFaultType.RTM_30_IS_NOT_VALID == putDataFaultMessage.getFaultInfo().getCode()) {
            if (LOG_INVALID_XML) {
                log.warn("OASU RPO said that the following XML is not valid:\n{}", rtm30Xml);
            }
            throw new Exception(represent, putDataFaultMessage);
        } else if (PutDataFaultType.DUPLICATE_MESSAGE == putDataFaultMessage.getFaultInfo().getCode()) {
            log.warn(represent);
        } else {
            throw new Exception(represent, putDataFaultMessage);
        }
    }

    private String represent(final PutDataFaultMessage putDataFaultMessage) {

        final String putDataFaultCode = putDataFaultMessage.getFaultInfo().getCode().value();
        final String putDataFaultDescription = putDataFaultMessage.getFaultInfo().getDescription();

        return "PutDataFault: " + putDataFaultCode + " - " + putDataFaultDescription;
    }

    /**
     * Clears parameters of the current client, so new one can be initialized.
     */
    @PreDestroy
    public void destroy() {

        log.info("OASU RPO web client termination.");
        authServicePort = null;
        dataServicePort = null;
        initialized = false;
    }
}
