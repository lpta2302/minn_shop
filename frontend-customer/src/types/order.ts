export interface OrderRequest{
    email: string
    firstName: string
    lastName: string
    phoneNumber: string
    shippingAddress: string
    paymentMethod: "CASH_ON_DELIVERY" | "VISA"
}