import { ReactElement } from 'react';
import { Translation } from 'react-i18next';

export function About(): ReactElement {
  const currentYear = new Date().getFullYear();

  return (
    <div>
      <div>
        <Translation>{(t) => t('aboutModal.description')}</Translation>
      </div>
      <br />
      <div>
        <Translation>{(t) => t('aboutModal.version')}</Translation>: v{import.meta.env.PACKAGE_VERSION} / ©{' '}
        {currentYear} «<Translation>{(t) => t('aboutModal.copyright')}</Translation>» /
      </div>
    </div>
  );
}
