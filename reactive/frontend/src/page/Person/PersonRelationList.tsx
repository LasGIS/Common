import React from 'react';
import { Button, Col, Row, Table, Tooltip } from 'antd';
import { DeleteOutlined, PlusOutlined } from '@ant-design/icons';
import { PersonRelationType, RelationType, SexType } from './reducer/types';
import { ColumnProps } from 'antd/lib/table/Column';
import { compareAlphabetically } from '../../utils';
import { ColumnsType } from 'antd/lib/table';
import { personRelationType, personToSex } from './reducer/utils';

type Props = {
  relations: RelationType[];
};
const PersonRelationList = ({ relations }: Props) => {
  const deleteRelation = (record: RelationType) => {
    console.log(`deleteRelation(${record.personToId}:)`);
  };

  const createColumns = (): ColumnProps<RelationType>[] => {
    return [
      {
        title: 'ФИО',
        dataIndex: 'personTo',
        width: 170,
        fixed: 'left',
        sorter: (a: RelationType, b: RelationType) => {
          return compareAlphabetically(a.personTo.fio, b.personTo.fio);
        },
        render: (value: string, record: RelationType) => record.personTo.fio,
      },
      {
        title: 'Отношение',
        dataIndex: 'type',
        width: 100,
        sorter: (a: RelationType, b: RelationType) => compareAlphabetically(`${a.type}`, `${b.type}`),
        render: (value: PersonRelationType, record: RelationType) => personRelationType(record.type, record?.personTo?.sex),
      },
      {
        title: 'Пол',
        dataIndex: 'sex',
        width: 100,
        sorter: (a: RelationType, b: RelationType) => compareAlphabetically(a.personTo.sex, b.personTo.sex),
        render: (value: SexType, record: RelationType) => personToSex(record.personTo),
      },
      {},
      {
        dataIndex: 'personId',
        fixed: 'right',
        width: 50,
        render: (value: unknown, record: RelationType) => (
          <Tooltip placement="topLeft" title={`Удалить связь ${record.personId}`}>
            <Button type="link" icon={<DeleteOutlined />} onClick={() => deleteRelation(record)} />
          </Tooltip>
        ),
      },
    ];
  };

  const columns: ColumnsType<RelationType> = createColumns();

  return (
    <>
      <Row style={{ marginBottom: 20 }}>
        <div style={{ flex: 'auto' }} />
        <Col>
          <Button style={{ alignItems: 'end' }} type="link" icon={<PlusOutlined />}>
            Добавить персону
          </Button>
        </Col>
      </Row>
      <Table<RelationType>
        rowKey={(record) => `relation_to_${record.personToId}`}
        scroll={{ y: 'calc(100vh - 433px)' }}
        dataSource={relations}
        columns={columns}
        size="small"
        pagination={false}
        bordered
      />
    </>
  );
};

export default PersonRelationList;
