import ProductDetailImageCarousel from "@/components/product/ProductDetailImageCarousel"
import Loading from "@/components/shared/Loading"
import { Button } from "@/components/ui/button"
import { getThumbnail } from "@/lib/imageUtils"
import { toVND } from "@/lib/stringUtils"
import { useAddToCart } from "@/tanstack/queries/cartQueries"
import { useGetProductWithFullVariantsById } from "@/tanstack/queries/productQueries"
import type { CartItemRequest } from "@/types/cart"
import type { ProductVariant } from "@/types/product"
import { HeartIcon } from "lucide-react"
import { useEffect, useMemo, useState } from "react"
import { useLocation, useNavigate } from "react-router"

const discount = Math.random() % 2

function ProductDetail() {
    const navigate = useNavigate()
    const location = useLocation()
    const state = location.state
    
    const [cartItem, setCartItem] = useState<undefined | CartItemRequest>(undefined)

    const {data: product, isLoading, isError} = useGetProductWithFullVariantsById(state?.productId)
    const {mutateAsync} = useAddToCart()
    const variants = useMemo(() => product?.productVariants, [product?.productVariants])
    

    const [currentVariant, setCurrentVariant] = useState<ProductVariant | undefined>(undefined)

    useEffect(() => {
        if (!state || !state.id || !state.slug) {
            navigate("/", { replace: true })
        }
    }, [state, navigate])
    
    useEffect(() => {
        if (!currentVariant && state?.id) {
            let variant = product?.productVariants.find(variant=>variant.id === state.id)
            if (!variant && product?.productVariants?.length) {
                variant = product?.productVariants[0]
            }

            setCurrentVariant(variant)
        }
    }, [currentVariant, product?.productVariants, state.id]);

    const handleAddToCart = async () =>{
    }

    if (!state || !state.id || !state.slug) return null


    if (!currentVariant || isLoading) {
        return <Loading/>
    }
    console.log(product);
    

    return (
        <div className="flex flex-col space-y-10 px-page_x py-10">
            <section className="flex w-full space-x-8">
                <div className="flex-1">
                    <ProductDetailImageCarousel images={currentVariant.productVariantImages} />
                </div>
                <div className="flex flex-col flex-1">
                    <h2 className="text-xl font-semibold">
                        {currentVariant.name}
                    </h2>
                    <p className="text-subtitle">
                        {product?.category.name}
                    </p>
                    <div className="flex space-x-2 mt-4">
                        {
                            discount &&
                            <p className="line-through text-subtitle">{
                                toVND(currentVariant.originalPrice)
                            }</p>
                        }
                        <p className="font-semibold">{
                            toVND(currentVariant.originalPrice * (1 - currentVariant.discount))
                        }</p>
                        {
                            discount &&
                            <p className="text-green-600 font-semibold">{Math.floor(currentVariant.discount * 100)}% off</p>
                        }
                    </div>
                    <div className="flex items-start space-x-4 mt-8">
                        {
                            variants?.map((variant) => (
                                <img
                                    key={variant.id}
                                    className={`
                                        size-15 rounded-sm
                                        cursor-pointer
                                        hover:opacity-80
                                        ${ currentVariant.id === variant?.id && "border-2 border-foreground opacity-80"}
                                    `}
                                    src={`${getThumbnail(variant.productVariantImages)?.url}`}
                                    alt="Your alt text"
                                    onClick={()=>setCurrentVariant(variant)}
                                />
                            ))
                        }
                    </div>
                    <div className="mt-8">
                        <h2 className="font-semibold">Select size</h2>
                        
                    </div>
                    <div className="flex max-w-sm flex-col space-y-4 mt-4">
                        <Button
                            onClick={handleAddToCart}
                            className="py-6 rounded-full"
                        >
                            Add to bag    
                        </Button>
                        <Button
                            className="py-6 rounded-full"
                            variant="outline"
                        >
                            Favourite
                            <HeartIcon/>
                        </Button>
                    </div>
                    <div className="max-w-sm mt-12 ">
                        {product?.description}
                    </div>
                    <div className="pt-8">
                        <h1 className="font-semibold text-lg">Review</h1>
                    </div>
                </div>
            </section>
            {/* <section className="mt-12">
                <ProductCarousel 
                    title="Related Products"
                    titleClassname="font-light"
                />
            </section> */}
        </div>
    )
}

export default ProductDetail