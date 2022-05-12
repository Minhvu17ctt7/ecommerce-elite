import classNames from 'classnames';
import { Button } from 'primereact/button';
import { Column } from 'primereact/column';
import { DataTable } from 'primereact/datatable';
import { Dialog } from 'primereact/dialog';
import { InputText } from 'primereact/inputtext';
import { RadioButton } from 'primereact/radiobutton';
import { Toast } from 'primereact/toast';
import { Toolbar } from 'primereact/toolbar';
import React, { useEffect, useRef, useState } from 'react';
import uploadImage from '../../firebase/upload';
import RoleService from '../../service/RoleService';
import UserService from '../../service/UserService';

const User = () => {
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

    const formatCurrency = (value) => {
        return value.toLocaleString('en-US', { style: 'currency', currency: 'USD' });
    }

    const openNew = () => {
        setProduct(emptyProduct);
        setSubmitted(false);
        setProductDialog(true);
    }

    const hideDialog = () => {
        setSubmitted(false);
        setProductDialog(false);
    }

    const hideDeleteProductDialog = () => {
        setDeleteProductDialog(false);
    }

    const hideDeleteProductsDialog = () => {
        setDeleteProductsDialog(false);
    }

    const saveProduct = async () => {
        setSubmitted(true);

        if (product.email.trim()) {
            try {
                if (mainImage != null) {
                    const urlImage = await uploadImage("/users", mainImage);
                    product['photo'] = urlImage
                }
                await UserService.createUser(product);
                toast.current.show({ severity: 'success', summary: 'Successful', detail: 'User Created', life: 3000 });
            }
            catch (e) {
                toast.current.show({ severity: 'error', summary: 'Error', detail: e.message, life: 3000 });
            }

            setProductDialog(false);
            setProduct(emptyProduct);
            forceFetchData();
        }
    }

    const editProduct = (product) => {
        product['categoryId'] = product.category.id;
        setProduct({ ...product });
        setProductDialog(true);
    }

    const confirmDeleteProduct = (product) => {
        setProduct(product);
        setDeleteProductDialog(true);
    }

    const confirmBlockProduct = (product) => {
        setProduct(product);
        setDeleteProductsDialog(true);
    }

    const deleteProduct = async () => {
        await UserService.deleteUser(product.id);
        setDeleteProductDialog(false);
        setProduct(emptyProduct);
        toast.current.show({ severity: 'success', summary: 'Successful', detail: 'User Deleted', life: 3000 });
        forceFetchData();
    }


    const updateBlockUser = async () => {
        try {
            await UserService.updateUserBlocked({ "id": product.id, "blocked": !product.blocked });
            toast.current.show({ severity: 'success', summary: 'Successful', detail: `${product.email} ${product.blocked ? "unblocked" : "blocked"}`, life: 3000 });
        } catch (e) {
            toast.current.show({ severity: 'error', summary: 'Error', detail: e.message, life: 3000 });
        }
        setDeleteProductsDialog(false);
        forceFetchData();
    }

    const onRoleChange = (e) => {
        let _product = { ...product };
        _product['role'] = e.value;
        setProduct(_product);
    }

    const onInputChange = (e, name) => {
        const val = (e.target && e.target.value) || '';
        let _product = { ...product };
        _product[`${name}`] = val;

        setProduct(_product);
    }

    const onInputNumberChange = (e, name) => {
        const val = e.value || 0;
        let _product = { ...product };
        _product[`${name}`] = val;

        setProduct(_product);
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

    const actionBodyTemplate = (rowData) => {
        return (
            <div className="actions">
                {/* <Button icon="pi pi-pencil" className="p-button-rounded p-button-success mr-2" onClick={() => editProduct(rowData)} /> */}
                <Button icon="pi pi-trash" className="p-button-rounded p-button-warning mt-2 mr-2" onClick={() => confirmDeleteProduct(rowData)} />
                <Button icon={rowData.blocked ? "pi pi-lock" : "pi pi-lock-open"} className={rowData.blocked ? "p-button-rounded p-button-secondary mt-2 mr-2" : "p-button-rounded p-button-success mt-2 mr-2"}
                    onClick={() => confirmBlockProduct(rowData)} />
            </div>
        );
    }

    const header = (
        <div className="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
            <h5 className="m-0">Manage User</h5>
            <span className="block mt-2 md:mt-0 p-input-icon-left">
                <i className="pi pi-search" />
                <InputText type="search" onInput={(e) => setGlobalFilter(e.target.value)} placeholder="Search..." />
            </span>
        </div>
    );

    const productDialogFooter = (
        <>
            <Button label="Cancel" icon="pi pi-times" className="p-button-text" onClick={hideDialog} />
            <Button label="Save" icon="pi pi-check" className="p-button-text" onClick={saveProduct} />
        </>
    );
    const deleteProductDialogFooter = (
        <>
            <Button label="No" icon="pi pi-times" className="p-button-text" onClick={hideDeleteProductDialog} />
            <Button label="Yes" icon="pi pi-check" className="p-button-text" onClick={deleteProduct} />
        </>
    );
    const blockUserDialogFooter = (
        <>
            <Button label="No" icon="pi pi-times" className="p-button-text" onClick={hideDeleteProductsDialog} />
            <Button label="Yes" icon="pi pi-check" className="p-button-text" onClick={updateBlockUser} />
        </>
    );


    const [mainImage, setMainImage] = useState(null);

    const handleChangeImage = (e) => {
        setMainImage(e.target.files[0]);
        const objectUrl = URL.createObjectURL(e.target.files[0])
        product['photo'] = objectUrl;
    }

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

                        {/* <Column field="inventoryStatus" header="Status" body={statusBodyTemplate} sortable headerStyle={{ width: '14%', minWidth: '10rem' }}></Column> */}
                        <Column body={actionBodyTemplate}></Column>
                    </DataTable>

                    <Dialog visible={productDialog} style={{ width: '450px' }} header="Create User" modal className="p-fluid" footer={productDialogFooter} onHide={hideDialog}>
                        <img src={product.photo ? product.photo : "/images/user-default.png"} alt={product.photo} width="150" className="mt-0 mx-auto mb-5 block shadow-2" />
                        <div className="field">
                            <label htmlFor="name">Image</label>
                            <input type="file" id="mainImage" required onChange={handleChangeImage} autoFocus className={classNames({ 'p-invalid': submitted && !product.name })} />
                            {submitted && !product.name && <small className="p-invalid">Image is required.</small>}
                        </div>
                        <div className="field">
                            <label htmlFor="name">Email</label>
                            <InputText id="name" value={product.email} onChange={(e) => { onInputChange(e, 'email') }} required autoFocus className={classNames({ 'p-invalid': submitted && !product.email })} />
                            {submitted && !product.email && <small className="p-invalid">Email is required.</small>}
                        </div>
                        <div className="field">
                            <label htmlFor="password">Password</label>
                            <InputText id="password" value={product.password} onChange={(e) => onInputChange(e, 'password')} required rows={3} cols={20} />
                        </div>
                        <div className="field">
                            <label htmlFor="repeatPassword">Repeat Password</label>
                            <InputText id="repeatPassword" value={product.repeatPassword} onChange={(e) => onInputChange(e, 'repeatPassword')} required rows={3} cols={20} />
                        </div>
                        <div className="field">
                            <label htmlFor="firstName">First Name</label>
                            <InputText id="firstName" value={product.firstName} onChange={(e) => { onInputChange(e, 'firstName') }} required autoFocus className={classNames({ 'p-invalid': submitted && !product.firstName })} />
                            {submitted && !product.firstName && <small className="p-invalid">firstName is required.</small>}
                        </div>
                        <div className="field">
                            <label htmlFor="lastName">Last Name</label>
                            <InputText id="lastName" value={product.lastName} onChange={(e) => { onInputChange(e, 'lastName') }} required autoFocus className={classNames({ 'p-invalid': submitted && !product.lastName })} />
                            {submitted && !product.lastName && <small className="p-invalid">LastName is required.</small>}
                        </div>
                        <div className="field">
                            <label className="mb-3">Role</label>
                            <div className="formgrid grid">
                                {
                                    roles.map(role => (
                                        <div key={role.id} className="field-radiobutton col-6">
                                            <RadioButton inputId={`role-${role.id}`} name="role" value={role.name} onChange={onRoleChange} checked={role.name === product.role} />
                                            <label htmlFor={`role-${role.id}`}>{role.name}</label>
                                        </div>
                                    ))
                                }

                            </div>
                        </div>


                    </Dialog>

                    <Dialog visible={deleteProductDialog} style={{ width: '450px' }} header="Confirm" modal footer={deleteProductDialogFooter} onHide={hideDeleteProductDialog}>
                        <div className="flex align-items-center justify-content-center">
                            <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
                            {product && <span>Are you sure you want to delete <b>{product.name}</b>?</span>}
                        </div>
                    </Dialog>

                    <Dialog visible={deleteProductsDialog} style={{ width: '450px' }} header="Confirm" modal footer={blockUserDialogFooter} onHide={hideDeleteProductsDialog}>
                        <div className="flex align-items-center justify-content-center">
                            <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
                            {product && <span>Are you sure you want to {product.blocked ? "unblock" : "block"} {product.email} ?</span>}
                        </div>
                    </Dialog>
                </div>
            </div>
        </div>
    );
}

const comparisonFn = function (prevProps, nextProps) {
    return prevProps.history.location.pathname === nextProps.history.location.pathname;
};

export default React.memo(User, comparisonFn);   