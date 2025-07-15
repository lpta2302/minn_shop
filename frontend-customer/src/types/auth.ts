import type { Cart } from "./cart";

export interface Customer {
  id: number; 
  firstName: string;
  lastName: string;
  fullName: string;
  phoneNumber?: string;
  shippingAddress?: string;
  dateOfBirth?: string; 

  accountId: number;
  email: string;
  role: "CUSTOMER" | []
  cart?: Cart;
}
