import React from 'react';
import { Link } from 'react-router-dom';
import classNames from 'classnames';
import { useHistory } from 'react-router-dom';

export const AppTopbar = (props) => {

    const history = useHistory();

    const handleLogout = () => {
        localStorage.removeItem('user');
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        localStorage.removeItem('isLogin');
        history.replace("/login")
    }

    return (
        <div className="layout-topbar">
            <Link to="/" className="layout-topbar-logo">

                <span>ADMIN MOSHOPPING</span>
            </Link>

            <button type="button" className="p-link  layout-menu-button layout-topbar-button" onClick={props.onToggleMenuClick}>
                <i className="pi pi-bars" />
            </button>

            <button type="button" className="p-link layout-topbar-menu-button layout-topbar-button" onClick={props.onMobileTopbarMenuClick}>
                <i className="pi pi-ellipsis-v" />
            </button>

            <ul className={classNames("layout-topbar-menu lg:flex origin-top", { 'layout-topbar-menu-mobile-active': props.mobileTopbarMenuActive })}>

                <li>
                    <button className="p-link layout-topbar-button" onClick={props.onMobileSubTopbarMenuClick}>
                        <i className="pi pi-user" />
                        <span>Profile</span>
                    </button>
                </li>
                <li>
                    <button className="p-link layout-topbar-button" onClick={handleLogout}>
                        <i className="pi pi-sign-out" />
                        <span>Logout</span>
                    </button>
                </li>
            </ul>
        </div>
    );
}
