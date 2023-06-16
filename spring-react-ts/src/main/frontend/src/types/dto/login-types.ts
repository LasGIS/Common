export enum Role {
  USER = 'ROLE_USER',
  ADMIN = 'ROLE_ADMIN',
}
export type ServiceUserDto = {
  username: string;
  fullName: string;
  roles: Role[];
};
