import { Separator } from "@/components/ui/separator";
import { default as links } from "@/constants/subTopBarLink";
import { Link } from "react-router";

const SubTopBarLinks = () => {
    return (
        <div className="flex items-center text-xs py-2">
            {links.map((link, index) => (

                <div className="flex items-center" key={index}>
                    {link.path && (
                        <Link to={link.path} className="hover:opacity-50">
                            {link.label}
                        </Link>
                    )}
                    {
                        links.length - 1 === index ||
                        <div className="flex items-center h-3">
                            <Separator orientation="vertical" className="bg-black mx-4" />
                        </div>
                    }
                </div>


            ))}
        </div>
    );
};


export default SubTopBarLinks;