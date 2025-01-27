const ul = document.querySelector('ul');
const fruits = ['Apple', 'Orange', 'Banana', 'Melon'];

const fragment = new DocumentFragment();

for (const fruit of fruits) {
  const li = document.createElement('li');
  li.textContent = fruit;
  fragment.append(li);
}

ul.append(fragment);
