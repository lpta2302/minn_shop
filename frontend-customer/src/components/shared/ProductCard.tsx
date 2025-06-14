import { useNavigate } from "react-router"
import {
    Card,
    CardHeader,
    CardContent,
    CardFooter
} from "../ui/card"
import { Button } from "../ui/button"
import { HeartIcon } from "lucide-react"
import { useState } from "react"

const imageUrl = "https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/ff58ef98-c8a1-43de-bba2-770062c58622/ENT+W+NK+DF+STAD+JSY+SS+AW.png"
const discount = Math.random() % 2 == 0
function ProductCard() {
    const [isLiked, setIsLiked] = useState<boolean>(false)
    const navigate = useNavigate()

    return (
        <Card
            className="gap-2 pt-0 cursor-pointer 
                [&:has(:not(*:hover))]:hover:opacity-80"
            onClick={() => {
                navigate("/product/abc", {state: {slug: 'abc', id: 1}})
            }
            }
        >
            <CardHeader className="p-0">
                <img
                    src={imageUrl}
                    alt="variant"
                />
            </CardHeader>
            <CardContent
                className="
                    px-2
                "
            >
                <h4 className="text-sm">Name</h4>
                <p className="text-xs text-subtitle">Short description</p>
                <div className="flex space-x-2">
                    {
                        discount &&
                        <p className="line-through text-subtitle">1.100.000đ</p>
                    }
                    <p
                        className={`
                            ${discount && 'red-500'}  
                        `}
                    >1.100.000đ</p>
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