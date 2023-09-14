import React, { useEffect } from 'react';
import { Button, Col, Form, Input, Modal, Row, Select } from 'antd';
import { useParams } from 'react-router';
import { ExclamationCircleOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import { deleteUserById, getUserById, selectCurrentUser } from './reducer';
import { AppDispatch } from '../../reducer/store';
import { useDispatch, useSelector } from 'react-redux';
import { UserRoleTypeOption, UserType } from './reducer/types';

const { Password } = Input;
const { confirm } = Modal;
const { Option } = Select;

const UserDetailForm = () => {
  const navigate = useNavigate();
  const dispatch: AppDispatch = useDispatch();
  const currentUser: UserType = useSelector(selectCurrentUser) as UserType;
  const { userId } = useParams<{ userId: string }>();
  const [form] = Form.useForm();

  useEffect(() => {
    if (userId) {
      dispatch(getUserById(Number(userId)) as AppDispatch);
    }
    /* eslint-disable */
  }, []);

  useEffect(() => {
    if (currentUser) {
      const fieldsHost = {
        userId: currentUser.userId,
        name: currentUser.name,
        login: currentUser.login,
        password: currentUser.password,
        archived: currentUser.archived,
        roles: currentUser.roles,
      };
      form.setFieldsValue(fieldsHost);
    }
  }, [form, currentUser]);

  const handleSubmit = () => {
    form.validateFields().then((values) => {
      if (currentUser) {
        const connectivity: UserType = {
          ...currentUser,
          userId: values.userId,
          name: values.name,
          login: values.login,
          password: values.password,
          archived: values.archived,
          roles: values.roles,
        };
        // onSave(connectivity);
        navigate({ pathname: '/user' });
      }
    });
  };

  const deleteRecord = (record: UserType) => {
    confirm({
      icon: <ExclamationCircleOutlined />,
      content: <div>Вы действительно хотите удалить пользователя: {record.userId}?</div>,
      onOk() {
        if (record.userId) dispatch(deleteUserById(record.userId) as AppDispatch);
      },
      onCancel() {
        console.log('onCancel()');
      },
    });
  };

  const handleClose = () => {
    navigate({ pathname: '/user' });
  };

  return (<>
    <Row>
      <Col style={{ paddingRight: 10 }}>
        <Form labelCol={{ span: 9 }} wrapperCol={{ span: 15 }} form={form}>
          <Form.Item name='userId' label='ID пользователя'>
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
            <Select mode='multiple' >
              {UserRoleTypeOption.map((option, index) => (
                <Option key={`sex_option_${index}`} value={option.code}>
                  {option.name}
                </Option>
              ))}
            </Select>
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

export default UserDetailForm;
