import React, { useEffect, useState } from 'react'
import { Table } from 'react-bootstrap'
import { useNavigate, useParams, useSearchParams } from 'react-router-dom'
import orderApi from '../../api/orderApi'
import Header from '../../components/Layout/Header'
import Pagination from '../../components/Pagination'
import { formatCurrency, convertTime } from '../../util/util'

const titleHeader = {
     mainTitle: 'ORDER',
     extraTitle: 'Order'
}

const Order = () => {
     const [searchParams] = useSearchParams();
     const page = searchParams.get("page") || 1;
     const pageSize = searchParams.get("pageSize") || 4;
     const [ordersPage, setOrdersPage] = useState();
     const navigate = useNavigate();

     useEffect(() => {
          (async () => {
               const ordersResponse = await orderApi.getListOrder(page, pageSize);
               setOrdersPage(ordersResponse.data);
          })()
     }, [page, pageSize])

     const handleNextPage = (nextPage) => {
          console.log("page next: ", nextPage)
          let url = `/orders?page=${nextPage}&pageSize=${pageSize}`;
          navigate(url);
     }

     return (
          <> <Header titleHeader={titleHeader} />
               <div className="container-fluid pt-5">
                    <div className="text-center mb-4">
                         <h2 className="section-title px-5"><span className="px-2">List Order</span></h2>
                    </div>
                    {
                         ordersPage?.data.length > 0 && (<div className="row px-xl-5">
                              <Table>
                                   <thead>
                                        <tr>
                                             <th>Id order</th>
                                             <th>Total price</th>
                                             <th>Total Item</th>
                                             <th>Status</th>
                                             <th>Order Day</th>
                                        </tr>
                                   </thead>
                                   <tbody>
                                        {
                                             ordersPage.data.map(order => (<tr>
                                                  <td>{order.id}</td>
                                                  <td>{formatCurrency(order.totalPrice)}</td>
                                                  <td>{order.totalItem}</td>
                                                  <td>{order.status}</td>
                                                  <td>{convertTime(order.createdDate)}</td>
                                             </tr>))
                                        }

                                   </tbody>
                              </Table>
                         </div>)
                    }
                    <div className="col-12 pb-1">
                         <nav aria-label="Page navigation">
                              {ordersPage?.totalPage > 1 && (<Pagination
                                   totalPage={parseInt(ordersPage?.totalPage)}
                                   currentPage={parseInt(page)}
                                   handleNextPage={handleNextPage}
                              />)}
                         </nav>
                    </div>
               </div>
          </>
     )
}

export default Order