import styled from 'styled-components';

const FooterWrapper = styled.footer`
  height: 24px;
  border-top: 1px solid #e5e5e5;
  background: #f5faff;
`;

const Footer = () => {
  return (
    <FooterWrapper>
      <span>Footer Content</span>
    </FooterWrapper>
  );
};

export default Footer;
