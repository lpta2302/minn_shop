import { Input } from "@/components/ui/input";
import type { Dispatch, SetStateAction } from "react";

export interface SearchProps {
    setIsSearching?: Dispatch<SetStateAction<boolean>>,
    isSearching?: boolean,
    setSearchParams: Dispatch<SetStateAction<object>>,
}

function MainSearchBar({ setIsSearching, isSearching, setSearchParams }: SearchProps) {
    return (
        <div className={`
                relative transition-all duration-500
                ${isSearching && 'w-full mx-4'}
                `}>
            <Input
                className={`
                transition-all duration-500
                ${isSearching ? 'w-[50%]' : 'w-full'}
                mx-auto
            `}
                placeholder="Search..."
                onFocus={() => setIsSearching?.(true)}
                onBlur={() => setIsSearching?.(false)}
                onChange={(e) =>
                    setSearchParams((prev) => ({ ...prev, search: e.target.value }))
                }
            />
        </div>
    )
}

export default MainSearchBar