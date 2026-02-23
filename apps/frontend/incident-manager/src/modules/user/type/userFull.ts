import { Group } from "@/modules/workGroup/type/group";
import { UserInfo } from "./userInfo";

export interface UserFull extends UserInfo {
    address: string;
    addressNumber: string;
    city: string;
    province: string;
    postalCode: string;
    phone: string;
    phoneBusiness: string;
    phoneExtension: string;
    email: string;
    active: boolean;
    groups: Group[];
}