let name = window.location.pathname.split('/').pop();
name = name?.substr(0, name?.length - 5);

export const tsxTab = {
  id: 'tsxTab',
  title: 'tsx',
  closable: true,
  content: <iframe src={`./${name}.tsx.html`} style={{ height: '100%', width: '100%' }} />
};


export const htmlTab = {
  id: 'htmlTab',
  title: 'html',
  closable: true,
  content: <iframe src={`./${name}.html.html`} style={{ height: '100%', width: '100%' }} />
};