import React from 'react';
import { ListBloc, Row } from '@/style/style.tsx';

interface ListProps {
  list: string[];
}

const List: React.FC<ListProps> = ({ list }) => {
  return list.length ? (
    <ListBloc>
      {list.map((str, index) => (
        <Row key={`l${index}`} style={{ overflow: 'hidden' }}>
          {str}
        </Row>
      ))}
    </ListBloc>
  ) : null;
};

export default List;
