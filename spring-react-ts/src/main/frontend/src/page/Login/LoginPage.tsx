/*
 *  @(#)LoginPage.tsx  last: 15.06.2023
 *
 * Title: LG prototype for spring-security + spring-data + react
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

import React from "react";
import { Button, Form, Input } from "antd";
import { showNotification } from "../../common/utils/notification-utils";
import styles from "./styles.module.scss";
import { LockOutlined, UserOutlined } from "@ant-design/icons";
import { useNavigate } from "react-router-dom";
import { ErrorDto } from "../../redux/common/types/util-types";
import { performLogin } from "./services/actions";

const LoginPage = () => {
  const navigate = useNavigate();
  const { from } = { from: { pathname: '/hosts', search: undefined } };

  const [form] = Form.useForm();

  const handleSubmit = () => {
    form.validateFields().then((values) => {
      performLogin(values.username, values.password)
        .then(() => {
          navigate({ pathname: from.pathname, search: from.search }, { replace: true });
        })
        .catch((error: ErrorDto) => {
          showNotification(error.message, error.detail, 'error');
        });
    });
  };

  return (
    <div className={styles.container}>
      <h1>Tracking Inventory</h1>
      <Form name="basic" form={form} onFinish={handleSubmit} title="Tracking Inventory">
        <Form.Item name="username" rules={[{ required: true, message: 'Введите логин' }]}>
          <Input prefix={<UserOutlined className="site-form-item-icon" />} placeholder="Логин" />
        </Form.Item>

        <Form.Item name="password" rules={[{ required: true, message: 'Введите пароль' }]}>
          <Input prefix={<LockOutlined className="site-form-item-icon" />} type="password" placeholder="Пароль" />
        </Form.Item>

        <Form.Item>
          <Button type="primary" htmlType="submit" className={styles.loginFormButton}>
            Войти
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
};

export default LoginPage;
