export interface GroupSave {
  name: string;
  description?: string;
  active?: boolean;
  userIds: string[];
}