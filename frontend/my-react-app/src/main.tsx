import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.css";
import AttendeeLandingPage from "./pages/attendee-landing-page.tsx";
import { AuthProvider } from "react-oidc-context";
import { createBrowserRouter, RouterProvider } from "react-router";
import OrganizersLandingPage from "./pages/organizers-landing-page.tsx";
import DashboardManageEventPage from "./pages/dashboard-manage-event-page.tsx";
import LoginPage from "./pages/login-page.tsx";
import ProtectedRoute from "./components/protected-route.tsx";
import CallbackPage from "./pages/callback-page.tsx";
import DashboardListEventsPage from "./pages/dashboard-list-events-page.tsx";
import PublishedEventsPage from "./pages/published-events-page.tsx";
import PurchaseTicketPage from "./pages/purchase-ticket-page.tsx";

const router = createBrowserRouter([
    {
        path: "/",
        Component: AttendeeLandingPage,
    },
    {
        path: "/callback",
        Component: CallbackPage,
    },
    {
        path: "/login",
        Component: LoginPage,
    },
    {
        path: "/events/:id",
        Component: PublishedEventsPage,
    },
    {
        path: "/events/:eventId/purchase/:ticketTypeId",
        element: (
            <ProtectedRoute>
                <PurchaseTicketPage />
            </ProtectedRoute>
        ),
    },
    {
        path: "/organizers",
        Component: OrganizersLandingPage,
    },
    {
        path: "/dashboard/events",
        element: (
            <ProtectedRoute>
                <DashboardListEventsPage />
            </ProtectedRoute>
        ),
    },
    {
        path: "/dashboard/events/create",
        element: (
            <ProtectedRoute>
                <DashboardManageEventPage />
            </ProtectedRoute>
        ),
    },
    {
        path: "/dashboard/events/update/:id",
        element: (
            <ProtectedRoute>
                <DashboardManageEventPage />
            </ProtectedRoute>
        ),
    },
]);

const oidcConfig = {
    authority: "http://localhost:9090/realms/event-ticket-platform",
    client_id: "event-ticket-platform-app",
    redirect_uri: "http://localhost:5173/callback",
};

createRoot(document.getElementById("root")!).render(
    <StrictMode>
        <AuthProvider {...oidcConfig}>
            <RouterProvider router={router} />
        </AuthProvider>
    </StrictMode>,
);
