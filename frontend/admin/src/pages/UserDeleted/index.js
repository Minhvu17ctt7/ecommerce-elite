import { Button } from 'primereact/button';
import { Column } from 'primereact/column';
import { DataTable } from 'primereact/datatable';
import { InputText } from 'primereact/inputtext';
import { Toast } from 'primereact/toast';
import { Toolbar } from 'primereact/toolbar';
import React, { useEffect, useRef, useState } from 'react';
import RoleService from '../../service/RoleService';
import UserService from '../../service/UserService';

const UserDeleted = () => {
    let emptyProduct = {
        id: null,
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        repeatPassword: '',
        role: '',
        photo: '',
        blocked: false
    };

    const [products, setProducts] = useState([]);
    const [roles, setRoles] = useState([]);
    const [productDialog, setProductDialog] = useState(false);
    const [deleteProductDialog, setDeleteProductDialog] = useState(false);
    const [deleteProductsDialog, setDeleteProductsDialog] = useState(false);
    const [product, setProduct] = useState(emptyProduct);
    const [selectedProducts, setSelectedProducts] = useState(null);
    const [submitted, setSubmitted] = useState(false);
    const [globalFilter, setGlobalFilter] = useState(null);
    const [countFetchData, setCountFetchDate] = useState(0);
    const toast = useRef(null);
    const dt = useRef(null);

    const forceFetchData = () => {
        setCountFetchDate(preState => preState + 1);
    }

    useEffect(() => {
        (async () => {

            const productsResponse = await UserService.getAllUser(false);
            const roleResponse = await RoleService.getAllRole();
            setProducts(productsResponse.data);
            setRoles(roleResponse.data);

        })()
    }, [countFetchData]);

    const openNew = () => {
        setProduct(emptyProduct);
        setSubmitted(false);
        setProductDialog(true);
    }

    const leftToolbarTemplate = () => {
        return (
            <React.Fragment>
                <div className="my-2">
                    <Button label="New" icon="pi pi-plus" className="p-button-success mr-2" onClick={openNew} />

                </div>
            </React.Fragment>
        )
    }

    const codeBodyTemplate = (rowData) => {
        return (
            <>
                <span className="p-column-title">Id</span>
                {rowData.id}
            </>
        );
    }

    const firstNameBodyTemplate = (rowData) => {
        return (
            <>
                <span className="p-column-title">Name</span>
                {rowData.firstName}
            </>
        );
    }

    const lastNameBodyTemplate = (rowData) => {
        return (
            <>
                <span className="p-column-title">Name</span>
                {rowData.lastName}
            </>
        );
    }

    const emailBodyTemplate = (rowData) => {
        return (
            <>
                <span className="p-column-title">Name</span>
                {rowData.email}
            </>
        );
    }
    const roleBodyTemplate = (rowData) => {
        return (
            <>
                <span className="p-column-title">Role</span>
                {rowData.roles.map(role => role.name)}
            </>
        );
    }

    const imageBodyTemplate = (rowData) => {
        return (
            <>
                <span className="p-column-title">Image</span>
                <img src={rowData.photo ? rowData.photo : "/images/user-default.png"} alt={rowData.image} className="shadow-2" width="100" />
            </>
        )
    }


    const header = (
        <div className="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
            <h5 className="m-0">Manage User Deleted</h5>
            <span className="block mt-2 md:mt-0 p-input-icon-left">
                <i className="pi pi-search" />
                <InputText type="search" onInput={(e) => setGlobalFilter(e.target.value)} placeholder="Search..." />
            </span>
        </div>
    );


    return (
        <div className="grid crud-demo">
            <div className="col-12">
                <div className="card">
                    <Toast ref={toast} />
                    <Toolbar className="mb-4" left={leftToolbarTemplate}></Toolbar>

                    <DataTable ref={dt} value={products} selection={selectedProducts} onSelectionChange={(e) => setSelectedProducts(e.value)}
                        dataKey="id" paginator rows={10} rowsPerPageOptions={[5, 10, 25]} className="datatable-responsive"
                        paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
                        currentPageReportTemplate="Showing {first} to {last} of {totalRecords} products"
                        globalFilter={globalFilter} emptyMessage="No products found." header={header} responsiveLayout="scroll">
                        <Column field="Id" header="Id" sortable body={codeBodyTemplate} headerStyle={{ width: '14%', minWidth: '10rem' }}></Column>
                        <Column header="Avatar" body={imageBodyTemplate} headerStyle={{ width: '14%', minWidth: '10rem' }}></Column>
                        <Column field="firstName" header="FirstName" sortable body={firstNameBodyTemplate} headerStyle={{ width: '14%', minWidth: '10rem' }}></Column>
                        <Column field="lastName" header="LastName" sortable body={lastNameBodyTemplate} headerStyle={{ width: '14%', minWidth: '10rem' }}></Column>
                        <Column field="email" header="Email" sortable body={emailBodyTemplate} headerStyle={{ width: '14%', minWidth: '10rem' }}></Column>
                        <Column field="role" header="Role" sortable body={roleBodyTemplate} headerStyle={{ width: '14%', minWidth: '10rem' }}></Column>
                    </DataTable>
                </div>
            </div>
        </div>
    );
}

const comparisonFn = function (prevProps, nextProps) {
    return prevProps.location.pathname === nextProps.location.pathname;
};

export default React.memo(UserDeleted, comparisonFn);   