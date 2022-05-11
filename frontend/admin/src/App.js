import classNames from 'classnames';
import 'primeflex/primeflex.css';
import 'primeicons/primeicons.css';
import PrimeReact from 'primereact/api';
import 'primereact/resources/primereact.css';
import { Tooltip } from 'primereact/tooltip';
import 'prismjs/themes/prism-coy.css';
import React, { useEffect, useRef, useState } from 'react';
import { Route, useLocation } from 'react-router-dom';
import { CSSTransition } from 'react-transition-group';
import './App.scss';
import { AppConfig } from './AppConfig';
import { AppFooter } from './AppFooter';
import { AppMenu } from './AppMenu';
import { AppTopbar } from './AppTopbar';
import './assets/demo/Demos.scss';
import './assets/demo/flags/flags.css';
import './assets/layout/layout.scss';
import Dashboard from './components/Dashboard';
import Category from './pages/Category';
import CategoryDeleted from './pages/CategoryDeleted';
import Login from './pages/Login';
import Product from './pages/Product';
import ProductDeleted from './pages/ProductDeleted';
import User from './pages/User';
import UserDeleted from './pages/UserDeleted';






const App = () => {
    const [layoutMode, setLayoutMode] = useState('static');
    const [layoutColorMode, setLayoutColorMode] = useState('light')
    const [inputStyle, setInputStyle] = useState('outlined');
    const [ripple, setRipple] = useState(true);
    const [staticMenuInactive, setStaticMenuInactive] = useState(false);
    const [overlayMenuActive, setOverlayMenuActive] = useState(false);
    const [mobileMenuActive, setMobileMenuActive] = useState(false);
    const [mobileTopbarMenuActive, setMobileTopbarMenuActive] = useState(false);
    const copyTooltipRef = useRef();
    const location = useLocation();

    PrimeReact.ripple = true;

    let menuClick = false;
    let mobileTopbarMenuClick = false;

    useEffect(() => {
        if (mobileMenuActive) {
            addClass(document.body, "body-overflow-hidden");
        } else {
            removeClass(document.body, "body-overflow-hidden");
        }
    }, [mobileMenuActive]);

    useEffect(() => {
        copyTooltipRef && copyTooltipRef.current && copyTooltipRef.current.updateTargetEvents();
    }, [location]);

    const onInputStyleChange = (inputStyle) => {
        setInputStyle(inputStyle);
    }

    const onRipple = (e) => {
        PrimeReact.ripple = e.value;
        setRipple(e.value)
    }

    const onLayoutModeChange = (mode) => {
        setLayoutMode(mode)
    }

    const onColorModeChange = (mode) => {
        setLayoutColorMode(mode)
    }

    const onWrapperClick = (event) => {
        if (!menuClick) {
            setOverlayMenuActive(false);
            setMobileMenuActive(false);
        }

        if (!mobileTopbarMenuClick) {
            setMobileTopbarMenuActive(false);
        }

        mobileTopbarMenuClick = false;
        menuClick = false;
    }

    const onToggleMenuClick = (event) => {
        menuClick = true;

        if (isDesktop()) {
            if (layoutMode === 'overlay') {
                if (mobileMenuActive === true) {
                    setOverlayMenuActive(true);
                }

                setOverlayMenuActive((prevState) => !prevState);
                setMobileMenuActive(false);
            }
            else if (layoutMode === 'static') {
                setStaticMenuInactive((prevState) => !prevState);
            }
        }
        else {
            setMobileMenuActive((prevState) => !prevState);
        }

        event.preventDefault();
    }

    const onSidebarClick = () => {
        menuClick = true;
    }

    const onMobileTopbarMenuClick = (event) => {
        mobileTopbarMenuClick = true;

        setMobileTopbarMenuActive((prevState) => !prevState);
        event.preventDefault();
    }

    const onMobileSubTopbarMenuClick = (event) => {
        mobileTopbarMenuClick = true;

        event.preventDefault();
    }

    const onMenuItemClick = (event) => {
        if (!event.item.items) {
            setOverlayMenuActive(false);
            setMobileMenuActive(false);
        }
    }
    const isDesktop = () => {
        return window.innerWidth >= 992;
    }

    const menu = [
        {
            items: [{
                label: 'Home', icon: 'pi pi-fw pi-home', to: '/'
            }]
        },
        {
            label: 'User', icon: 'pi pi-fw pi-sitemap',
            items: [
                { label: 'User', icon: 'pi pi-fw pi-home', to: '/users' },
                { label: 'User Deleted', icon: 'pi pi-fw pi-check-square', to: '/users-Deleted' },
            ]
        },
        {
            label: 'Category', icon: 'pi pi-fw pi-sitemap',
            items: [
                { label: 'Category', icon: 'pi pi-fw pi-home', to: '/categories' },
                { label: 'Category Deleted', icon: 'pi pi-fw pi-check-square', to: '/categories-deleted' },
            ]
        },
        {
            label: 'Product', icon: 'pi pi-fw pi-sitemap',
            items: [
                { label: 'Product', icon: 'pi pi-fw pi-home', to: '/products' },
                { label: 'Product Deleted', icon: 'pi pi-fw pi-check-square', to: '/products-deleted' },
            ]
        }
    ];

    const addClass = (element, className) => {
        if (element.classList)
            element.classList.add(className);
        else
            element.className += ' ' + className;
    }

    const removeClass = (element, className) => {
        if (element.classList)
            element.classList.remove(className);
        else
            element.className = element.className.replace(new RegExp('(^|\\b)' + className.split(' ').join('|') + '(\\b|$)', 'gi'), ' ');
    }

    const wrapperClass = classNames('layout-wrapper', {
        'layout-overlay': layoutMode === 'overlay',
        'layout-static': layoutMode === 'static',
        'layout-static-sidebar-inactive': staticMenuInactive && layoutMode === 'static',
        'layout-overlay-sidebar-active': overlayMenuActive && layoutMode === 'overlay',
        'layout-mobile-sidebar-active': mobileMenuActive,
        'p-input-filled': inputStyle === 'filled',
        'p-ripple-disabled': ripple === false,
        'layout-theme-light': layoutColorMode === 'light'
    });

    const headerExclusionArray = [
        'login',
    ];


    let splitPathName = location.pathname.split('/');

    return (
        <div className={wrapperClass} onClick={onWrapperClick}>
            <Tooltip ref={copyTooltipRef} target=".block-action-copy" position="bottom" content="Copied to clipboard" event="focus" />

            {headerExclusionArray.indexOf(splitPathName[1]) < 0 &&
                (<><AppTopbar onToggleMenuClick={onToggleMenuClick} layoutColorMode={layoutColorMode}
                    mobileTopbarMenuActive={mobileTopbarMenuActive} onMobileTopbarMenuClick={onMobileTopbarMenuClick} onMobileSubTopbarMenuClick={onMobileSubTopbarMenuClick} />

                    <div className="layout-sidebar" onClick={onSidebarClick}>
                        <AppMenu model={menu} onMenuItemClick={onMenuItemClick} layoutColorMode={layoutColorMode} />
                    </div></>)}

            <div className="layout-main-container">
                <div className="layout-main">
                    <Route path="/" exact render={() => <Dashboard colorMode={layoutColorMode} location={location} />} />
                    <Route path="/products" component={Product} />
                    <Route path="/products-deleted" component={ProductDeleted} />
                    <Route path="/categories" component={Category} />
                    <Route path="/categories-deleted" component={CategoryDeleted} />
                    <Route path="/users" component={User} />
                    <Route path="/users-deleted" component={UserDeleted} />
                    <Route path="/login" component={Login} />
                </div>
                {headerExclusionArray.indexOf(splitPathName[1]) < 0 &&
                    <AppFooter layoutColorMode={layoutColorMode} />}
            </div>


            <AppConfig rippleEffect={ripple} onRippleEffect={onRipple} inputStyle={inputStyle} onInputStyleChange={onInputStyleChange}
                layoutMode={layoutMode} onLayoutModeChange={onLayoutModeChange} layoutColorMode={layoutColorMode} onColorModeChange={onColorModeChange} />

            <CSSTransition classNames="layout-mask" timeout={{ enter: 200, exit: 200 }} in={mobileMenuActive} unmountOnExit>
                <div className="layout-mask p-component-overlay"></div>
            </CSSTransition>

        </div>
    );

}

export default App;
