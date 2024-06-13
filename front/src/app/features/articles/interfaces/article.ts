export interface Article {
  id: number;
  title: string;
  content: string;
  username: string;
  userId: number;
  themeTitle: string;
  themeId: number;
  commentIds: number[];
  created_at: Date;
  updated_at: Date;
}
