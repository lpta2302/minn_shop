import { Separator } from "@/components/ui/separator";
import { default as links } from "@/constants/subTopBarLink";
import { useAuthContext } from "@/context/AuthContext";
import { Link } from "react-router";

const AccountLink = ()=>{
}

const SubTopBarLinks = () => {
    const {user, isAuthenticated} = useAuthContext()
    console.log(user);
    

    return (
        <div className="flex items-center text-xs py-2">
            {links.map((link, index) => (

                <div className="flex items-center" key={index}>
                    {
                        link.path &&
                        link.path === '/sign-in' && isAuthenticated && user.fullName ?
                        user.fullName :
                        <Link to={link.path} className="hover:opacity-50">
                            {link.label}
                        </Link>
                    }
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