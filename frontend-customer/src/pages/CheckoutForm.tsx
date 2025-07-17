import { useForm } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Label } from "@/components/ui/label";
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group";
import { Card, CardContent } from "@/components/ui/card";
import { useNavigate } from "react-router";
import type { CartItem } from "@/types/cart";
import { getSessionStorage } from "@/lib/clientStorage";
import { toast } from "sonner";
import { usePlaceOrder } from "@/tanstack/queries/orderQueries";

// 1. Zod schema
const orderSchema = z.object({
  email: z.email("Invalid email address"),
  firstName: z.string().min(2, "First name must be at least 2 characters"),
  lastName: z.string().min(2, "Last name must be at least 2 characters"),
  phoneNumber: z
    .string()
    .min(8, "Phone number must be at least 8 digits")
    .max(15, "Phone number too long"),
  shippingAddress: z
    .string()
    .min(10, "Shipping address must be at least 10 characters"),
  paymentMethod: z.enum(["CASH_ON_DELIVERY", "VISA"]),
});

type OrderFormData = z.infer<typeof orderSchema>;

export default function CheckoutForm() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<OrderFormData>({
    resolver: zodResolver(orderSchema),
  });
  const navigate = useNavigate()
  const {mutateAsync: placeOrder, isPending} = usePlaceOrder()
//   const { cartItems } = useCartContext();
  const cartItems = getSessionStorage<CartItem[]>('selectedItems')


  if (!cartItems) {
    navigate("/")
  }

  const total = cartItems?.reduce(
    (sum, item) => sum + item.quantity * (item.productVariant.finalPrice ?? 0),
    0
  );

  const onSubmit = async (data: OrderFormData) => {
    const orderRequest = {
      shippingAddress: data.shippingAddress,
      paymentMethod: data.paymentMethod,
      items: cartItems?.map((item) => ({
        productVariantId: item.productVariant.id,
        stockOptionValueId: item.stockOptionValue.id,
        quantity: item.quantity,
      })),
    };

    console.log("Submitting order:", orderRequest);

    await placeOrder(data)
    toast("Order has been placed.",
        {description: "Your order will be accepted in day and delivery in 3 days depends on your location.",
            action: {
            label: "Check order",
            onClick: () => navigate('/orders'),
            },
            position: "top-center",
            className: "text-md",
        }
    )
    navigate("/")
  };

  return (
    <form
      onSubmit={handleSubmit(onSubmit)}
      noValidate
      className="flex max-md:flex-col w-full mx-auto xl:px-64 xl:py-32 lg:p-16 px-4 py-12 md:gap-32"
    >
      {/* Left Column - Delivery Info */}
      <div className="flex flex-col gap-4 w-full">
        <h1 className="text-2xl font-semibold">Delivery</h1>

        {/* Email */}
        <div className="space-y-2">
          <Label htmlFor="email">Email *</Label>
          <Input
            id="email"
            type="email"
            placeholder="Enter your email"
            className="py-6"
            {...register("email")}
          />
          {errors.email && (
            <p className="text-sm text-red-500">{errors.email.message}</p>
          )}
        </div>

        {/* First name */}
        <div className="space-y-2">
          <Label htmlFor="firstName">First name *</Label>
          <Input
            id="firstName"
            type="text"
            placeholder="Enter your first name"
            className="py-6"
            {...register("firstName")}
          />
          {errors.firstName && (
            <p className="text-sm text-red-500">{errors.firstName.message}</p>
          )}
        </div>

        {/* Last name */}
        <div className="space-y-2">
          <Label htmlFor="lastName">Last name *</Label>
          <Input
            id="lastName"
            type="text"
            placeholder="Enter your last name"
            className="py-6"
            {...register("lastName")}
          />
          {errors.lastName && (
            <p className="text-sm text-red-500">{errors.lastName.message}</p>
          )}
        </div>

        {/* Phone number */}
        <div className="space-y-2">
          <Label htmlFor="phoneNumber">Phone number *</Label>
          <Input
            id="phoneNumber"
            type="tel"
            placeholder="Enter your phone number"
            className="py-6"
            {...register("phoneNumber")}
          />
          {errors.phoneNumber && (
            <p className="text-sm text-red-500">{errors.phoneNumber.message}</p>
          )}
        </div>

        {/* Shipping address */}
        <div className="space-y-2">
          <Label htmlFor="shippingAddress">Shipping address *</Label>
          <Input
            id="shippingAddress"
            type="text"
            placeholder="Enter your shipping address"
            className="py-6"
            {...register("shippingAddress")}
          />
          {errors.shippingAddress && (
            <p className="text-sm text-red-500">
              {errors.shippingAddress.message}
            </p>
          )}
        </div>

        {/* Payment Method */}
        <div className="space-y-2">
          <Label>Payment Method *</Label>
          <RadioGroup defaultValue="CASH_ON_DELIVERY">
            <div className="flex items-center space-x-2">
              <RadioGroupItem
                value="CASH_ON_DELIVERY"
                id="cod"
                {...register("paymentMethod")}
              />
              <Label htmlFor="cod">Cash on Delivery</Label>
            </div>
            <div className="flex items-center space-x-2">
              <RadioGroupItem
                value="VISA"
                id="visa"
                {...register("paymentMethod")}
              />
              <Label htmlFor="visa">VISA</Label>
            </div>
          </RadioGroup>
          {errors.paymentMethod && (
            <p className="text-sm text-red-500">
              {errors.paymentMethod.message}
            </p>
          )}
        </div>
      </div>

      {/* Right Column - Cart Summary */}
      <div className="w-full">
        <div>
          <h2 className="text-lg font-semibold mb-2">Order Items</h2>
          <Card>
            <CardContent className="divide-y">
              {cartItems?.map((item, index) => (
                item.isSelected &&
                <div key={index} className="py-4 flex gap-4">
                  <img
                    src={item.productVariant.thumbnail ?? "/placeholder.jpg"}
                    alt={item.productVariant.name}
                    className="w-16 h-16 object-cover rounded"
                  />
                  <div className="flex-1">
                    <p className="font-medium">{item.productVariant.name}</p>
                    <p className="text-sm text-gray-500">x{item.quantity}</p>
                  </div>
                  <p className="font-semibold whitespace-nowrap">
                    {(item.quantity * (item.productVariant.finalPrice ?? 0)).toLocaleString("vi-VN")}₫
                  </p>
                </div>
              ))}
            </CardContent>
          </Card>

          <div className="flex justify-between mt-4 mb-4 font-semibold">
            <span>Total</span>
            <span>{total?.toLocaleString("vi-VN")}₫</span>
          </div>
        </div>

        <Button
          type="submit"
          className="w-full bg-black text-white py-6 text-base rounded-full"
          disabled={isPending}
        >
          {isPending ? "Placing order..." : "Place Order"}
        </Button>
      </div>
    </form>
  );
}
