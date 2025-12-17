import { UserInfo } from "@/modules/user/type/userInfo";
import { GroupListItem } from "./groupListItem";

export interface GroupFull extends GroupListItem {
    users: UserInfo[],
    availableUsers: UserInfo[]
}