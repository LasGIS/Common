import React, { Component } from 'react';

type Props = {};

type State = {
  count: number;
};

class SomeComponent extends Component<Props, State> {
  constructor(props: Props) {
    super(props);
    // this.handleClick = this.handleClick.bind(this);
    this.state = {
      count: 0,
    };
  }

  handleClick(event: React.MouseEvent<HTMLButtonElement, MouseEvent>) {
    this.setState((state) => ({ count: state.count + 1 }));
    console.log(event.target);
  }

  render() {
    const { count } = this.state;
    return (
      <div>
        <button type="button" onClick={(event) => this.handleClick(event)}>
          Click Me
        </button>
        <p>
          <strong className="label">Count:</strong>
          {count || '(empty)'}
        </p>
      </div>
    );
  }
}

export default SomeComponent;
