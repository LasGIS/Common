import './styles.css';
import { Button, Form, Input } from 'antd';
import { LockOutlined, UserOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import { ErrorDto } from '../../types/types';
import { performLogin } from './services/actions';

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
          console.log(`showNotification(${error.message}, ${error.detail});`);
          //showNotification(error.message, error.detail, 'error');
        });
    });
  };

  return (
    <div className="container">
      <h1>Tracking Inventory</h1>
      <Form name="basic" form={form} onFinish={handleSubmit} title="Basic Authority">
        <Form.Item name="username" rules={[{ required: true, message: 'Введите логин' }]}>
          <Input prefix={<UserOutlined className="site-form-item-icon" />} placeholder="Логин" />
        </Form.Item>

        <Form.Item name="password" rules={[{ required: true, message: 'Введите пароль' }]}>
          <Input prefix={<LockOutlined className="site-form-item-icon" />} type="password" placeholder="Пароль" />
        </Form.Item>

        <Form.Item>
          <Button type="primary" htmlType="submit" className="login-form-button">
            Войти
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
};

export default LoginPage;
