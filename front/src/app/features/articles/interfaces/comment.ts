
export interface Comment {
  id: number;
  content: string;
  userId: number;
  username: string;
  articleId: number;
  created_at: Date;
  updated_at: Date;
}