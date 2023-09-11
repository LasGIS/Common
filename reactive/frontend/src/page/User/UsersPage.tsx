import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { Button, Col, Modal, Row, Table, Tooltip } from 'antd';
import Highlighter from 'react-highlight-words';
import { compareAlphabetically, compareBoolean } from '../../utils';
import { ColumnProps } from 'antd/lib/table/Column';
import { DeleteOutlined, EditOutlined, ExclamationCircleOutlined } from '@ant-design/icons/lib';
import SearchInputWithDelay from '../components/SearchInputWithDelay';
import { getSearchWords } from '../../utils/SearchValueUtils';
import { ColumnsType } from 'antd/lib/table';
import { AppDispatch, useAppDispatch } from '../../reducer/store';
import { UserType } from './reducer/types';
import { deleteUserById, getAllUsers, selectAllUsers, selectSearchValue } from './reducer';
import { useSelector } from 'react-redux';

const { confirm } = Modal;
// const { Option } = Select;

const UsersPage = () => {
  const navigate = useNavigate();
  const dispatch: AppDispatch = useAppDispatch();
  const allUsers: UserType[] = useSelector(selectAllUsers) as UserType[];
  const search: string = useSelector(selectSearchValue) as string;

  useEffect(() => {
    dispatch(getAllUsers() as AppDispatch);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const editRecord = (userId: number) => {
    navigate(`/user/${userId}`);
  };

  const deleteRecord = (record: UserType) => {
    confirm({
      icon: <ExclamationCircleOutlined />,
      content: <div>Вы действительно хотите удалить пользователя: {record.login}?</div>,
      onOk() {
        if (record.userId) dispatch(deleteUserById(record.userId) as AppDispatch);
      },
      onCancel() {
        console.log('onCancel()');
      },
    });
  };

  const createColumns = (): ColumnProps<UserType>[] => {
    const searchLowered = search.toLowerCase();

    const renderWithHighlightSingle = (text: string) => (
      <Highlighter
        highlightStyle={{
          backgroundColor: '#ffc069',
          padding: 0,
        }}
        searchWords={getSearchWords(searchLowered)}
        autoEscape
        textToHighlight={text || ''}
      />
    );

    const renderWithHighlight = (text: string) => <div style={{ whiteSpace: 'pre-line' }}>{renderWithHighlightSingle(text)}</div>;

    const columns: ColumnProps<UserType>[] = [
      {
        title: 'Имя учетной записи',
        dataIndex: 'name',
        fixed: 'left',
        sorter: (a: UserType, b: UserType) => compareAlphabetically(a.name, b.name),
        render: (value: string, record: UserType) => (
          <Tooltip placement="topLeft" title={`Просмотреть запись пользователя  "${record.login}"`}>
            <Button type="link" icon={<EditOutlined />} onClick={() => editRecord(record.userId)}>
              {renderWithHighlightSingle(value)}
            </Button>
          </Tooltip>
        ),
      },
      {
        title: 'Login',
        dataIndex: 'login',
        sorter: (a: UserType, b: UserType) => compareAlphabetically(a.login, b.login),
        render: renderWithHighlight,
      },
      {
        title: 'is Archived',
        dataIndex: 'archived',
        sorter: (a: UserType, b: UserType) => compareBoolean(a.archived, b.archived),
        render: renderWithHighlight,
      },
    ];

    columns.push({
      dataIndex: 'username',
      fixed: 'right',
      width: 50,
      render: (value: unknown, record: UserType) => (
        <Tooltip placement="topLeft" title={`Удалить группу ${record.login}`}>
          <Button type="link" icon={<DeleteOutlined />} onClick={() => deleteRecord(record)} />
        </Tooltip>
      ),
    });

    return columns;
  };

  const columns: ColumnsType<UserType> = createColumns();

  return (
    <>
      <Row justify="center">
        <Col style={{ width: '100%' }}>
          <Row>
            <h2>Пользователи</h2>
            <div style={{ flex: 'auto' }} />
          </Row>
          <Row style={{ marginBottom: 20 }}>
            <Col span={12}>
              <SearchInputWithDelay
                onChange={(value: string, mandatory: boolean) => {
                  console.log(`(value: ${value}, mandatory: ${mandatory})`);
                }}
                placeholder="Поиск"
                value={search}
              />
            </Col>
            <div style={{ flex: 'auto' }} />
          </Row>
          <Table<UserType>
            rowKey={(record) => record.login}
            scroll={{ x: 1000, y: 'calc(100vh - 433px)' }}
            dataSource={allUsers}
            columns={columns}
            size="small"
            pagination={false}
            bordered
          />
        </Col>
      </Row>
    </>
  );
};

export default UsersPage;
