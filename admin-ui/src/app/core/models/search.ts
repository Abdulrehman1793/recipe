export interface Search {
  action: 'page' | 'sort';
  sort: string;
  direction: string;
  page: number;
  pageSize: number;
}
