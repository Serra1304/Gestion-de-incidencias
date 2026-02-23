import { GroupListItem } from "@/modules/workGroup/type/groupListItem";
import { SelectOption } from "../type/selectOption";

export function mapGroupsToOptions(
  groups: GroupListItem[]
): SelectOption[] {
  return groups.map(g => ({
    label: g.name,
    value: g.id,
  }));
}