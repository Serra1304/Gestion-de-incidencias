export interface UserUpsertDTO {
    name?: string;
    lastName?: string;
    secondLastName?: string;
    address?: string;
    addressNumber?: string;
    city?: string;
    province?: string;
    postalCode?: string;
    phone?: string;
    phoneBusiness?: string;
    phoneExtension?: string;
    email?: string;
    active?: boolean;
    groups?: String[];
}