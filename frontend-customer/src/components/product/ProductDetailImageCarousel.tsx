import { Carousel, CarouselContent, CarouselItem, CarouselNext, CarouselPrevious, type CarouselApi } from "../ui/carousel"
import { useState } from "react"

const slides = Array.from({ length: 5 })

function ProductDetailImageCarousel() {
    const [api, setApi] = useState<CarouselApi>(undefined)

    return (
        <div className="flex space-x-4">
            <div className="flex flex-col space-y-2">
                {
                    slides.map((_, index) => (
                        <img
                            className="size-15 rounded-sm"
                            src={`https://picsum.photos/600/350?v=${index}`}
                            alt="Your alt text"
                            onMouseEnter={()=>api?.scrollTo(index, true)}
                        />
                    ))
                }
            </div>
            <div className="flex-1">
                <Carousel
                    setApi={setApi}
                    // plugins={
                    //     [
                    //         Autoplay({
                    //             delay: 2500,
                    //             stopOnInteraction: false
                    //         }),
                    //     ]}
                    className="w-full relative">
                    <CarouselContent>
                        {slides.map((_, index) => (
                            <CarouselItem key={index}>
                                <img
                                    className="w-full h-[80vh] rounded-sm"
                                    src={`https://picsum.photos/600/350?v=${index}`}
                                    alt="Your alt text"
                                />
                            </CarouselItem>
                        ))}
                    </CarouselContent>
                    <CarouselPrevious className="absolute top-1/2 left-1"/>
                    <CarouselNext className="absolute top-1/2 right-1"/>
                    {/* <div className="
                inline-flex items-center bg-[rgba(35,35,35,0.5)] rounded-sm px-2 space-x-1 py-0.5
                absolute bottom-1 left-1/2    
            ">
                        {
                            slides.map((_, index) =>
                                isShowing == index ?
                                    <Circle key={index} className="transition-all duration-200" size={13} color="transparent" fill="#dedede" /> :
                                    <Circle key={index} className="transition-all duration-200" size={13} color="#dedede" onClick={() => handleSelectSlide(index)} />
                            )
                        }
                    </div> */}
                </Carousel>
            </div>
        </div>
    )
}

export default ProductDetailImageCarousel