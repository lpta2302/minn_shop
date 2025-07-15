import { Button } from "@/components/ui/button"
import { Card, CardContent } from "@/components/ui/card"
import { useCart } from "@/context/CartContext"
import type { CartItem } from "@/types/cart"
import { Separator } from "@/components/ui/separator"
import { Trash2, Minus, Plus } from "lucide-react"
import { useMemo, useState } from "react"
import { toVND } from "@/lib/stringUtils"


function Cart() {
    const { cartItems } = useCart()
    const [items, setItems] = useState<CartItem[] | []>(cartItems)
    
    const subtotal = useMemo(
        () => items.reduce((acc, item)=>acc+=item.productVariant.finalPrice,0), 
    [])

    return (
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6 p-6 max-w-6xl mx-auto">
            {/* Bag Section */}
            <div className="lg:col-span-2">
                <h2 className="text-2xl font-semibold mb-4">Bag</h2>
                <div className="flex flex-col gap-3">
                    {
                        items?.map(item => (
                            <div className="flex flex-col gap-2 py-2">
                                <div className="flex flex-col md:flex-row gap-4 w-full">
                                    {/* Product Image */}
                                    <img
                                        src={item.productVariant.thumbnail} // replace with real path
                                        alt={item.productVariant.name}
                                        className="rounded-xl w-40 h-40 object-cover"
                                    />
                                    {/* Info */}
                                    <div className="flex flex-col gap-2 flex-1">
                                        {/* //TODO: need product name */}
                                        <div className="font-semibold">{"PRODUCT NAME"}</div>
                                        <div className="text-sm text-gray-600">{item.productVariant.variantId}</div>
                                        <div className="text-sm text-gray-600">{item.productVariant.name}</div>
                                        <div className="text-subtitle">
                                            Size <span className="underline px-2">{item.productOptionValue.name}</span>
                                        </div>
                                    </div>
                                    <span className="font-semibold whitespace-nowrap">
                                        {toVND(item.productVariant.finalPrice)}
                                    </span>

                                </div>                                    
                                {/* Quantity & Action */}
                                <div className="flex items-center gap-2">
                                    <Button variant="ghost" size="icon">
                                        <Trash2 className="w-4 h-4" />
                                    </Button>
                                    <div className="flex items-center gap-2 border px-1 py-0.5 rounded-full">
                                        <Button variant="ghost" size="icon">
                                            <Minus className="w-3 h-3" />
                                        </Button>
                                        <span className="text-sm">{item.quantity}</span>
                                        <Button variant="ghost" size="icon">
                                            <Plus className="w-3 h-3" />
                                        </Button>
                                    </div>
                                </div>
                                <Separator className="mt-6"/>
                            </div>
                        ))
                    }
                </div>
            </div>

            {/* Summary Section */}
            <div>
                <h2 className="text-2xl font-semibold mb-4">Summary</h2>
                <Card>
                    <CardContent className="p-4 space-y-4">
                        <div className="flex justify-between text-sm">
                            <span>Subtotal</span>
                            <span>{toVND(subtotal)}</span>
                        </div>
                        <div className="flex justify-between text-sm">
                            <span>Estimated Delivery & Handling</span>
                            <span>250,000₫</span>
                        </div>
                        <Separator />
                        <div className="flex justify-between font-semibold text-base">
                            <span>Total</span>
                            <span>{toVND(subtotal)}</span>
                        </div>
                        <Separator />
                        <div className="flex flex-col gap-2 pt-4">
                            <Button className="w-full py-6 rounded-full bg-black text-white hover:bg-gray-800">
                                Checkout
                            </Button>
                        </div>
                    </CardContent>
                </Card>
            </div>
        </div>
    )
}

export default Cart