export interface Ingredient {
  id: number;
  description: string;
  amount: number;
  unitOfMeasure: { id: number; uom: string };
}
