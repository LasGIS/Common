import { useState } from 'react';

function SomeFunctional() {
  const [color] = useState<string>('red');
  return <div style={{ background: color }}>hhhhh</div>;
}

export default SomeFunctional;
