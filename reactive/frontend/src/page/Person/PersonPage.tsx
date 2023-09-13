import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Button, Col, Modal, Row, Table, Tooltip } from "antd";
import Highlighter from "react-highlight-words";
import { compareAlphabetically } from "../../utils";
import { ColumnProps } from "antd/lib/table/Column";
import { DeleteOutlined, EditOutlined, ExclamationCircleOutlined } from "@ant-design/icons/lib";
import SearchInputWithDelay from "../components/SearchInputWithDelay";
import { getSearchWords } from "../../utils/SearchValueUtils";
import { ColumnsType } from "antd/lib/table";
import { AppDispatch, useAppDispatch } from "../../reducer/store";
import { PersonType } from "./reducer/types";
import { deletePersonById, getAllPersons, selectAllPersons, selectSearchValue } from "./reducer";
import { useSelector } from "react-redux";

const { confirm } = Modal;
// const { Option } = Select;

const PersonPage = () => {
  const navigate = useNavigate();
  const dispatch: AppDispatch = useAppDispatch();
  const allPersons: PersonType[] = useSelector(selectAllPersons) as PersonType[];
  const search: string = useSelector(selectSearchValue) as string;

  useEffect(() => {
    dispatch(getAllPersons() as AppDispatch);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const editRecord = (personId: number) => {
    navigate(`/person/${personId}`);
  };

  const deleteRecord = (record: PersonType) => {
    confirm({
      icon: <ExclamationCircleOutlined />,
      content: <div>Вы действительно хотите удалить пользователя: {record.firstName}?</div>,
      onOk() {
        if (record.personId) dispatch(deletePersonById(record.personId) as AppDispatch);
      },
      onCancel() {
        console.log('onCancel()');
      },
    });
  };

  const createColumns = (): ColumnProps<PersonType>[] => {
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

    const columns: ColumnProps<PersonType>[] = [
      {
        title: 'ФИО персонажа',
        dataIndex: 'firstName',
        fixed: 'left',
        sorter: (a: PersonType, b: PersonType) =>
          compareAlphabetically(`${a.firstName} ${a.middleName} ${a.lastName}`, `${b.firstName} ${b.middleName} ${b.lastName}`),
        render: (value: string, record: PersonType) => (
          <Tooltip placement="topLeft" title={`Просмотреть запись пользователя  "${record.firstName} ${record.middleName} ${record.lastName}"`}>
            <Button type="link" icon={<EditOutlined />} onClick={() => editRecord(record.personId)}>
              {renderWithHighlightSingle(`${record.firstName} ${record.middleName} ${record.lastName}`)}
            </Button>
          </Tooltip>
        ),
      },
      {
        title: 'Пол',
        dataIndex: 'sex',
        sorter: (a: PersonType, b: PersonType) => compareAlphabetically(a.sex, b.sex),
        render: renderWithHighlight,
      },
    ];

    columns.push({
      dataIndex: 'personId',
      fixed: 'right',
      width: 50,
      render: (value: unknown, record: PersonType) => (
        <Tooltip placement="topLeft" title={`Удалить персону ${record.personId}`}>
          <Button type="link" icon={<DeleteOutlined />} onClick={() => deleteRecord(record)} />
        </Tooltip>
      ),
    });

    return columns;
  };

  const columns: ColumnsType<PersonType> = createColumns();

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
          <Table<PersonType>
            rowKey={(record) => record.personId}
            scroll={{ x: 1000, y: 'calc(100vh - 433px)' }}
            dataSource={allPersons}
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

export default PersonPage;
