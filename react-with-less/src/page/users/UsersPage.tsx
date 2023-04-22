import React from 'react';

const UsersPage = () => {
  const [name, setName] = React.useState('');

  return (
    <div>
      <form>
        <label className="label" htmlFor="name-field">
          Name:
        </label>
        <input
          id="name-field"
          value={name}
          onChange={(event) => {
            setName(event.target.value);
          }}
        />
      </form>

      <p>
        <strong className="label">Current value:</strong>
        {name || '(empty)'}
      </p>
    </div>
  );
};

export default UsersPage;
