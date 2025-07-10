import { useCallback, useEffect, useState } from "react"
import { CarouselContent, CarouselItem, Carousel, type CarouselApi } from "../ui/carousel"
import { ChevronLeft, ChevronRight } from "lucide-react"
import ProductCard from "../shared/ProductCard"
import { Button } from "../ui/button"
import { cn } from "@/lib/utils"
import type { ProductVariant } from "@/types/product"

interface CanScroll {
    canScrollNext: boolean
    canScrollPrev: boolean
}

interface ProductCarouselProps{
    title?: string
    titleClassname?:string
    products: ProductVariant[]
}

function ProductCarousel({title, titleClassname, products}:ProductCarouselProps) {
    const [api, setApi] = useState<CarouselApi>()
    const [canScroll, setCanScroll] = useState<CanScroll>({ canScrollNext: true, canScrollPrev: true })

    const scroll = useCallback((isScrollNext: boolean) => {
        if (!api) {
            return
        }

        if (isScrollNext) {
            api.scrollNext()
        } else {
            api.scrollPrev()
        }

        setCanScroll({ canScrollNext: api.canScrollNext(), canScrollPrev: api.canScrollPrev() })
    }, [api])

    useEffect(() => {
        if (api == undefined) {
            return
        }
        setCanScroll({
            canScrollNext: api?.canScrollNext(),
            canScrollPrev: api?.canScrollPrev(),
        })
    }, [api]);

    return (
        <div>
            {
                title &&
                <h1 className={cn("text-3xl font-semibold", titleClassname)}>
                    {title}
                </h1>
            }
            <div className="flex justify-end space-x-2 mb-2">
                <Button
                    data-slot="carousel-prev"
                    variant="outline"
                    className="rounded-full size-12"
                    size="icon"
                    disabled={!canScroll.canScrollPrev}
                    onClick={() => scroll(false)}
                >
                    <ChevronLeft className="size-5" />
                    <span className="sr-only">Next slide</span>
                </Button>
                <Button
                    data-slot="carousel-next"
                    variant="outline"
                    className="rounded-full size-12"
                    size="icon"
                    disabled={!canScroll.canScrollNext}
                    onClick={() => scroll(true)}
                >
                    <ChevronRight className="size-5" />
                    <span className="sr-only">Next slide</span>
                </Button>
            </div>
            <Carousel
                setApi={setApi}
                className="w-full relative"
            >
                <CarouselContent>
                    {products.map((product, index) => (
                        <CarouselItem className="sm:basis-1/2 lg:basis-1/3 xl:basis-1/4" key={index}>
                            <ProductCard product={product} />
                        </CarouselItem>
                    ))}
                </CarouselContent>
            </Carousel>
        </div>
    )
}

export default ProductCarousel