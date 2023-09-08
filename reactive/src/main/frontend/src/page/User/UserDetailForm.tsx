import React, { useEffect } from 'react';
import { Button, Col, Descriptions, Form, Input, Modal, Row, Select } from 'antd';
import cn from 'classnames';
import { useParams } from 'react-router';
import { ExclamationCircleOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import { DateTimeFormat } from '../../utils';
import { deleteUserById, getUserById, selectCurrentUser } from './reducer';
import { AppDispatch, useAppDispatch } from '../../reducer/store';
import { useSelector } from 'react-redux';
import { UserType } from './reducer/types';

const { TextArea } = Input;

const { confirm } = Modal;

const UserDetailForm = () => {
  const navigate = useNavigate();
  const dispatch: AppDispatch = useAppDispatch();
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
        navigate({ pathname: "/user" });
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
        console.log("onCancel()");
      },
    });
  };

  const handleClose = () => {
    navigate({ pathname: "/user" });
  };

  return (
    <Row>
      <Col span={14} style={{ paddingRight: 10 }}>
        <Form layout="vertical" form={form}>
          <Form.Item
            name="username"
            label="Имя учетной записи"
            rules={[
              {
                required: true,
                message: "Имя учетной записи обязательно!",
              },
            ]}
          >
            <Input disabled />
          </Form.Item>
          <Form.Item name="skills" label="Навыки">
            <Input />
          </Form.Item>
          <Form.Item name="website" label="Персональный веб-сайт">
            <Input />
          </Form.Item>
          <Form.Item name="biography" label="Биография">
            <TextArea rows={4} />
          </Form.Item>
        </Form>
      </Col>
      <Col span={10} style={{ paddingLeft: 10 }}>
        <Descriptions colon column={1} labelStyle={{ width: 140 }}>
          <Descriptions.Item label="Системная роль">Пользователь Решения</Descriptions.Item>
          <Descriptions.Item label="Системная роль">
            <Select mode="tags" placeholder="Please select" style={{ width: "100%" }} />
          </Descriptions.Item>
        </Descriptions>
      </Col>
      <Col span={24}>
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
      </Col>
    </Row>
  );
};

export default UserDetailForm;
