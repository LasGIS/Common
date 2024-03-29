import './Counter.css';
import React from 'react';
import { Button, Space } from 'antd';

const Counter = () => {
  const [count, setCount] = React.useState(0);
  /*
  React.useEffect(() => {
    setCount(0);
  }, [page]);
*/

  const handleCount = () => {
    setCount(count + 1);
  };

  return (
    <div className={'counter'}>
      <Button onClick={handleCount}>Increase</Button>
      {`Count: ${count}`}
    </div>
  );
};

const PageView = ({ page }: { page: number }) => (
  <div className={'data'}>
    {`Page: ${page}`}
    <Counter />
  </div>
);

const CounterPage = () => {
  const [page, setPage] = React.useState(1);

  const handleNext = () => {
    setPage(page + 1);
  };

  const handlePrevious = () => {
    if (page != 1) {
      setPage(page - 1);
    }
  };

  return (
    <div className={'container'}>
      <Space.Compact block>
        <Button block onClick={handlePrevious}>
          Previous
        </Button>
        <Button block onClick={handleNext}>
          Next
        </Button>
      </Space.Compact>
      <PageView page={page} />
    </div>
  );
};

export default CounterPage;
