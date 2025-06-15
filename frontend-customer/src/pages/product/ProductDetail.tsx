import ProductCarousel from "@/components/home/ProductCarousel"
import ProductDetailImageCarousel from "@/components/product/ProductDetailImageCarousel"
import Loading from "@/components/shared/Loading"
import { Button } from "@/components/ui/button"
import { toVND } from "@/lib/stringUtils"
import { HeartIcon } from "lucide-react"
import { useEffect, useState } from "react"
import { useLocation, useNavigate } from "react-router"

const discount = Math.random() % 2

interface ProductVariant{
    id: number
    name: string
    category: string
    originalPrice: number
    discount: number
    description: string
}

const defaultVariant : ProductVariant = {
    id: 1,
    name: "Jordan Sport",
    category: "Women's Tunnel Trousers",
    originalPrice: 2759000,
    discount: 0.2,
    description: `Players have the tunnel walk. 
    You have the pavement. 
    Both are the perfect opportunity to get a 
    'fit off. The easy, relaxed cut and 4-way 
    stretch material lets these trousers move 
    with you while maintaining their shape (and your look). 
    Gathered jogger-style cuffs break perfectly to showcase 
    that pair of J's you just added to your rotation.
    Colour Shown: Medium Olive/Cargo Khaki
    Style: FB4659-222
    Country/Region of Origin: China"`
}

const variants : ProductVariant[] = [
    defaultVariant,
    {...defaultVariant, id: 2}
]

function ProductDetail() {
    const navigate = useNavigate()
    const location = useLocation()
    const state = location.state

    const [currentVariant, setCurrentVariant] = useState<ProductVariant | undefined>(undefined)

    useEffect(() => {
        if (!state || !state.id || !state.slug) {
            navigate("/", { replace: true })
        }
    }, [state, navigate])
    
    useEffect(() => {
        if (!currentVariant) {
            setCurrentVariant(defaultVariant)    
        }
    }, [currentVariant]);

    if (!state || !state.id || !state.slug) return null


    if (!currentVariant) {
        return <Loading/>
    }

    return (
        <div className="flex flex-col space-y-10 px-page_x py-10">
            <section className="flex w-full space-x-8">
                <div className="flex-1">
                    <ProductDetailImageCarousel />
                </div>
                <div className="flex flex-col flex-1">
                    <h2 className="text-xl font-semibold">
                        {currentVariant.name}
                    </h2>
                    <p className="text-subtitle">
                        {currentVariant.category}
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
                            variants.map((variant, index) => (
                                <img
                                    className={`
                                        size-15 rounded-sm
                                        cursor-pointer
                                        hover:opacity-80
                                        ${ currentVariant.id === variant?.id && "border-2 border-foreground opacity-80"}
                                    `}
                                    src={`https://picsum.photos/600/350?v=${index}`}
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
                        {currentVariant.description}
                    </div>
                    <div className="pt-8">
                        <h1 className="font-semibold text-lg">Review</h1>
                    </div>
                </div>
            </section>
            <section className="mt-12">
                <ProductCarousel 
                    title="Related Products"
                    titleClassname="font-light"
                />
            </section>
        </div>
    )
}

export default ProductDetail