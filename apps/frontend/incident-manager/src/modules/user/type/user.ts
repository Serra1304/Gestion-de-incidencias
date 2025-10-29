export type User = {
    id: string;
    name: string;
    firstSurname: string;
    secondSurname: string;
    email: string;
    password?: string;
    rolesId?: string[];
    groupsId?: string[];
}