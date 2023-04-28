import { useState } from 'react';
import { groupByField } from '../test/imTesting';
import { arr1 } from '../test/data';

const handler = () => {
  groupByField(arr1, 'country');
};

function SomeFunctional() {
  const [color] = useState<string>('red');
  return (
    <div style={{ background: color }} onClick={handler}>
      Click
    </div>
  );
}

export default SomeFunctional;
