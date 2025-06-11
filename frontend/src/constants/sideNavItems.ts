import { IconCategory2, type Icon} from "@tabler/icons-react"

export interface SideNavItemType{
    title: string
    url: string
    icon: Icon
}

export const managementItems : SideNavItemType[] = [
    {
        title: 'Category',
        url: 'manage/category',
        icon: IconCategory2
    }
]