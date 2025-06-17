import Loading from "@/components/shared/Loading";
import { NavigationMenu } from "@/components/ui/navigation-menu"
import { NavigationMenuContent, NavigationMenuItem, NavigationMenuLink, NavigationMenuList, NavigationMenuTrigger } from "@/components/ui/navigation-menu"
import { useGetAllDisplayedCategories } from "@/tanstack/queries/categoryQueries";
import type { Category } from "@/types/category";
import { useEffect, useState } from "react";

function MainTopNavigation() {
    const { data: categoryResponse, isLoading, isError } = useGetAllDisplayedCategories()
    const [categories, setCategories] = useState<Category[] | undefined>(undefined)

    useEffect(() => {
        setCategories(categoryResponse?.content)

    }, [categoryResponse?.content]);

    if (isLoading || !categoryResponse) {
        return <Loading />
    }

    return (
        <NavigationMenu viewport={false}>
            <NavigationMenuList className="select-none">
                {
                    categories?.map((category, index) => (
                        <NavigationMenuItem key={index}>
                            {
                                category.subCategories?.length ?
                                <>
                                    <NavigationMenuTrigger>
                                        {category.name}
                                    </NavigationMenuTrigger>
                                        
                                        <NavigationMenuContent>
                                            <ul>
                                                {
                                                    category?.subCategories?.map(
                                                        (subCategory) => (
                                                            <li>
                                                                <NavigationMenuLink asChild>
                                                                    <div>
                                                                        {subCategory.name}
                                                                    </div>
                                                                </NavigationMenuLink>
                                                            </li>
                                                        )
                                                    )
                                                }
                                            </ul>
                                        </NavigationMenuContent>
                                </> :
                                <NavigationMenuLink>
                                    {category.name}
                                </NavigationMenuLink>
                            }
                        </NavigationMenuItem>
                    ))
                }
            </NavigationMenuList>
        </NavigationMenu>
    )
}

export default MainTopNavigation