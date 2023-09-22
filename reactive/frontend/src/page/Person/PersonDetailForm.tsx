import React, { useEffect } from 'react';
import { Button, Col, Form, Input, Modal, Row, Select } from 'antd';
import { useParams } from 'react-router';
import { ExclamationCircleOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import { deletePersonById, getPersonById, selectCurrentPerson, selectIsNewPerson } from './reducer';
import { AppDispatch } from '../../reducer/store';
import { useDispatch, useSelector } from 'react-redux';
import { PersonType, SexTypeOption } from './reducer/types';
import PersonRelationList from './PersonRelationList';

const { confirm } = Modal;
const { Option } = Select;

const PersonDetailForm = () => {
  const navigate = useNavigate();
  const dispatch: AppDispatch = useDispatch();
  const currentPerson: PersonType = useSelector(selectCurrentPerson) as PersonType;
  const isNewPerson: boolean = useSelector(selectIsNewPerson) as boolean;
  const { personId } = useParams<{ personId: string }>();
  const [form] = Form.useForm();

  useEffect(() => {
    if (personId) {
      dispatch(getPersonById(Number(personId)) as AppDispatch);
    }
  }, [dispatch, personId]);

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
        const person: PersonType = {
          ...currentPerson,
          personId: values.personId,
          firstName: values.firstName,
          lastName: values.lastName,
          middleName: values.middleName,
          sex: values.sex,
          relations: values.relations,
        };
        // onSave(person);
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

  return (
    <>
      <Row>
        <Col style={{ paddingRight: 20, width: 250 }}>
          <Form labelCol={{ span: 9 }} wrapperCol={{ span: 15 }} form={form}>
            <Form.Item name="personId" label="ID персоны">
              <Input disabled={!isNewPerson} />
            </Form.Item>
            <Form.Item name="firstName" label="Имя" rules={[{ required: true, message: 'Имя обязательно!' }]}>
              <Input />
            </Form.Item>
            <Form.Item name="middleName" label="Отчество" rules={[{ required: true, message: 'Имя обязательно!' }]}>
              <Input />
            </Form.Item>
            <Form.Item name="lastName" label="Фамилия" rules={[{ required: true, message: 'Фамилия обязательно!' }]}>
              <Input />
            </Form.Item>
            <Form.Item name="sex" label="Пол">
              <Select>
                {SexTypeOption.map((option, index) => (
                  <Option key={`sex_option_${index}`} value={option.code}>
                    {option.name}
                  </Option>
                ))}
              </Select>
            </Form.Item>
          </Form>
        </Col>
        {currentPerson && currentPerson.relations && (
          <Col style={{ width: 'calc(100% - 250px)' }}>
            <PersonRelationList relations={currentPerson.relations} />
          </Col>
        )}
      </Row>
      <Row justify="center">
        <Form.Item noStyle>
          <Button type="primary" htmlType="submit" onClick={handleSubmit}>
            Сохранить
          </Button>
          <Button style={{ marginLeft: 8 }} htmlType="reset" onClick={handleClose}>
            Закрыть
          </Button>
        </Form.Item>
      </Row>
    </>
  );
};

export default PersonDetailForm;
