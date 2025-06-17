import { useNavigate } from "react-router"
import {
    Card,
    CardHeader,
    CardContent,
    CardFooter
} from "../ui/card"
import { Button } from "../ui/button"
import { HeartIcon } from "lucide-react"
import { useCallback, useMemo, useState } from "react"
import type { ProductVariant, ProductVariantImage } from "@/types/productVariant"
import { toVND } from "@/lib/stringUtils"

function ProductCard({ product }: { product: ProductVariant }) {
    const [isLiked, setIsLiked] = useState<boolean>(false)
    const navigate = useNavigate()

    const getThumbnail = useCallback(
        (images: ProductVariantImage[]) => {
            if (images.length < 0) {
                return
            }

            const thumbnail = images.find(image => image.isThumbnail)
            if (thumbnail) {
                return thumbnail
            } else {
                return images[0]
            }
        },
        [],
    )

    const { discount, finalPrice } = useMemo(() => {
        const percentDiscountPrice: number = product.originalPrice * (1 - product.discount)
        if (percentDiscountPrice <= product.price) {
            return { discount: product.discount, finalPrice: percentDiscountPrice }
        } if (product.originalPrice <= product.price || product.price === 0) {
            return { discount: 0, finalPrice: product.originalPrice }
        } else {
            return { discount: Math.round(product.originalPrice / product.price - 1), finalPrice: product.price }
        }

    }, [product?.discount, product?.originalPrice, product?.price])



    return (
        <Card
            className="gap-2 pt-0 cursor-pointer 
                [&:has(:not(*:hover))]:hover:opacity-80"
            onClick={() => {
                navigate("/product/abc", { state: { slug: product.slug, id: product.id } })
            }
            }
        >
            <CardHeader className="p-0">
                {
                    product.productVariantImages.length > 0 ?
                        <img
                            src={getThumbnail(product.productVariantImages)?.url}
                            alt={product.name}
                        /> :
                        <div className="bg-white-smoke" />
                }
            </CardHeader>
            <CardContent
                className="
                    px-2
                "
            >
                <h4 className="text-md font-medium">Name</h4>
                <p className="text-sm text-subtitle">Short description</p>
                <div className="flex space-x-2 w-full justify-end">
                    {
                        discount &&
                        <p className="line-through text-subtitle">
                            {toVND(product.originalPrice)}
                        </p>
                    }
                    <p
                        className={`font-semibold
                            ${discount && 'text-red-600'}  
                        `}
                    >
                        {
                            toVND(finalPrice)
                        }
                    </p>
                </div>
            </CardContent>
            <CardFooter
                className="
                    justify-end
                    px-2
                "
            >
                <Button
                    onClick={(e) => {
                        e.stopPropagation()
                        setIsLiked(prev => !prev)
                    }}
                    variant="ghost"
                    size="icon"
                    className="rounded-full p-0"
                >
                    <HeartIcon
                        className="size-6 border-primary"
                        fill={isLiked ? "primary" : "transparent"}
                    />
                </Button>
            </CardFooter>
        </Card>
    )
}

export default ProductCard