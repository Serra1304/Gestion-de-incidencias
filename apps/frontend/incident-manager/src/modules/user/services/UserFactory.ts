import { UserFull } from "../type/userFull";

export async function createEmptyUser(): Promise<UserFull> {
  return {
    id: "",
    name: "",
    lastName: "",
    secondLastName: "",
    address: "",
    addressNumber: "",
    city: "",
    province: "",
    postalCode: "",
    phone: "",
    phoneBusiness: "",
    phoneExtension: "",
    email: "",
    active: true,
    groups: [],
  };
}