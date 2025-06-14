import { NavigationMenu } from "@/components/ui/navigation-menu"
import { NavigationMenuContent, NavigationMenuItem, NavigationMenuLink, NavigationMenuList, NavigationMenuTrigger } from "@/components/ui/navigation-menu"

const categories: { label: string, id?: number, subCategories?: { label: string, id?: number }[] }[] = [
    {
        label: "Boy",
        subCategories: [
            {
                label: "shirt",
            },
            {
                label: "pants",
            },
        ]
    },
    {
        label: "Girl",
        subCategories: [
            {
                label: "Skirt",
            },
            {
                label: "Dress",
            },
        ]
    },
]

function MainTopNavigation() {
    return (
        <NavigationMenu viewport={false}>
            <NavigationMenuList>
                {
                    categories.map((category, index) => (
                        <NavigationMenuItem key={index}>
                            {
                                category.subCategories?.length &&
                                <>
                                    <NavigationMenuTrigger>
                                        {category.label}
                                    </NavigationMenuTrigger>
                                    <NavigationMenuContent>
                                        <ul>
                                            {
                                                category.subCategories.map(
                                                    (subCategory)=>(
                                                        <li>
                                                            <NavigationMenuLink asChild>
                                                                <div>
                                                                    {subCategory.label}
                                                                </div>
                                                            </NavigationMenuLink>
                                                        </li>
                                                    )
                                                )
                                            }
                                        </ul>
                                    </NavigationMenuContent>
                                </>
                            }
                        </NavigationMenuItem>
                    ))
                }
            </NavigationMenuList>
        </NavigationMenu>
    )
}

export default MainTopNavigation