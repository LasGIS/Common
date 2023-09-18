import React from 'react';
import { Button, Col, Row, Table, Tooltip } from 'antd';
import { DeleteOutlined, PlusOutlined } from '@ant-design/icons';
import { PersonRelationType, RelationType, SexType } from './reducer/types';
import { ColumnProps } from 'antd/lib/table/Column';
import { compareAlphabetically } from '../../utils';
import { ColumnsType } from 'antd/lib/table';
import { personRelationType, personToFio, personToSex } from './reducer/utils';

type Props = {
  relations: RelationType[];
};
const PersonRelationDetailForm = ({ relations }: Props) => {
  const deleteRelation = (record: RelationType) => {
    console.log(`deleteRelation(${record.personToId}:)`);
  };

  const createColumns = (): ColumnProps<RelationType>[] => {
    const columns: ColumnProps<RelationType>[] = [
      {
        title: 'ФИО персонажа',
        dataIndex: 'personTo',
        width: 300,
        // fixed: 'left',
        sorter: (a: RelationType, b: RelationType) => {
          return compareAlphabetically(personToFio(a.personTo), personToFio(b.personTo));
        },
        render: (value: string, record: RelationType) => personToFio(record.personTo),
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
    ];

    columns.push({
      dataIndex: 'personId',
      fixed: 'right',
      width: 50,
      render: (value: unknown, record: RelationType) => (
        <Tooltip placement="topLeft" title={`Удалить связь ${record.personId}`}>
          <Button type="link" icon={<DeleteOutlined />} onClick={() => deleteRelation(record)} />
        </Tooltip>
      ),
    });

    return columns;
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

export default PersonRelationDetailForm;
