import React, { useEffect } from "react";
import { Button, Col, Form, Input, Modal, Row, Select } from "antd";
import { useParams } from "react-router";
import { ExclamationCircleOutlined } from "@ant-design/icons";
import { useNavigate } from "react-router-dom";
import { deletePersonById, getPersonById, selectCurrentPerson } from "./reducer";
import { AppDispatch, useAppDispatch } from "../../reducer/store";
import { useSelector } from "react-redux";
import { PersonType } from "./reducer/types";

const { Password } = Input;

const { confirm } = Modal;

const PersonDetailForm = () => {
  const navigate = useNavigate();
  const dispatch: AppDispatch = useAppDispatch();
  const currentPerson: PersonType = useSelector(selectCurrentPerson) as PersonType;
  const { personId } = useParams<{ personId: string }>();
  const [form] = Form.useForm();

  useEffect(() => {
    if (personId) {
      dispatch(getPersonById(Number(personId)) as AppDispatch);
    }
    /* eslint-disable */
  }, []);

  useEffect(() => {
    if (currentPerson) {
      const fieldsHost = {
        personId: currentPerson.personId,
        firstName: currentPerson.firstName,
        lastName: currentPerson.lastName,
        middleName: currentPerson.middleName,
        sex: currentPerson.sex,
        relations: currentPerson.relations,
      };
      form.setFieldsValue(fieldsHost);
    }
  }, [form, currentPerson]);

  const handleSubmit = () => {
    form.validateFields().then((values) => {
      if (currentPerson) {
        const connectivity: PersonType = {
          ...currentPerson,
          personId: values.personId,
          firstName: values.firstName,
          lastName: values.lastName,
          middleName: values.middleName,
          sex: values.sex,
          relations: values.relations,
        };
        // onSave(connectivity);
        navigate({ pathname: '/person' });
      }
    });
  };

  const deleteRecord = (record: PersonType) => {
    confirm({
      icon: <ExclamationCircleOutlined />,
      content: <div>Вы действительно хотите удалить пользователя: {record.personId}?</div>,
      onOk() {
        if (record.personId) dispatch(deletePersonById(record.personId) as AppDispatch);
      },
      onCancel() {
        console.log('onCancel()');
      },
    });
  };

  const handleClose = () => {
    navigate({ pathname: '/person' });
  };

  return (<>
    <Row>
      <Col style={{ paddingRight: 10 }}>
        <Form layout='horizontal' form={form}>
          <Form.Item name='personId' label='ID пользователя'>
            <Input disabled />
          </Form.Item>
          <Form.Item
            name='name'
            label='Имя учетной записи'
            rules={[
              {
                required: true,
                message: 'Имя учетной записи обязательно!',
              },
            ]}
          >
            <Input />
          </Form.Item>
          <Form.Item name='login' label='Логин'>
            <Input />
          </Form.Item>
          <Form.Item name='password' label='Пароль'>
            <Password />
          </Form.Item>
          <Form.Item name='archived' label='Архивный'>
            <Input />
          </Form.Item>
          <Form.Item name='roles' label='Роли'>
            <Select mode='multiple' />
          </Form.Item>
        </Form>
      </Col>
    </Row>
    <Row justify='center'>
      <Form.Item noStyle>
        <Button type='primary' htmlType='submit' onClick={handleSubmit}>
          Сохранить
        </Button>
        <Button style={{ marginLeft: 8 }} htmlType='reset' onClick={handleClose}>
          Закрыть
        </Button>
      </Form.Item>
    </Row>
  </>);
};

export default PersonDetailForm;
