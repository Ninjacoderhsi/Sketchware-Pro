package a.a.a;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.besome.sketch.beans.ImageBean;
import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.TextBean;
import com.besome.sketch.beans.ViewBean;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.regex.Pattern;

import dev.aldi.sayuti.editor.injection.AppCompatInjection;

public class Ox {

    public jq a;
    public AppCompatInjection aci;
    public ProjectFileBean b;
    public ViewBean c;
    public ArrayList<ViewBean> d;
    public Nx e = null;
    public Nx f = null;

    public Ox(jq jqVar, ProjectFileBean projectFileBean) {
        a = jqVar;
        b = projectFileBean;
        aci = new AppCompatInjection(jqVar, projectFileBean);
    }

    public static String x(String str) {
        if (!str.contains(".")) {
            return str;
        }
        String[] split = str.split("\\.");
        return split[split.length - 1];
    }

    /**
     * @return The parameter String escaped properly for XML strings
     */
    public final String a(String str) {
        CharBuffer buffer = CharBuffer.wrap(str);
        StringBuilder result = new StringBuilder(str.length());
        while (buffer.hasRemaining()) {
            char got = buffer.get();
            switch (got) {
                case '?':
                    result.append("\\?");
                    break;

                case '@':
                    result.append("\\@");
                    break;

                case '\"':
                    result.append("&quot;");
                    break;

                case '&':
                    result.append("&amp;");
                    break;

                case '<':
                    result.append("&lt;");
                    break;

                case '>':
                    result.append("&gt;");
                    break;

                case '\n':
                    result.append("\\n");
                    break;

                default:
                    result.append(got);
                    break;
            }
        }
        return result.toString();
    }

    public final void a() {
        Nx nx = new Nx("LinearLayout");
        nx.a("android", "layout_width", "match_parent");
        nx.a("android", "layout_height", "match_parent");
        nx.a("android", "orientation", "vertical");
        for (ViewBean viewBean : d) {
            String parent = viewBean.parent;
            if (parent == null || parent.length() <= 0 || parent.equals("root")) {
                b(nx, viewBean);
            }
        }
        if (a.g) {
            if (b.fileType == ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY) {
                if (b.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_TOOLBAR)) {
                    nx.a("app", "layout_behavior", "@string/appbar_scrolling_view_behavior");
                }
                if (b.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_TOOLBAR)
                        || b.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_FAB)) {
                    Nx coordinatorLayoutTag = new Nx("androidx.coordinatorlayout.widget.CoordinatorLayout");
                    coordinatorLayoutTag.a("android", "id", "@+id/_coordinator");
                    aci.inject(coordinatorLayoutTag, "CoordinatorLayout");
                    e = coordinatorLayoutTag;
                }
                if (b.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_TOOLBAR)) {
                    Nx toolbarTag = new Nx("androidx.appcompat.widget.Toolbar");
                    toolbarTag.a("android", "id", "@+id/_toolbar");
                    aci.inject(toolbarTag, "Toolbar");
                    Nx appBarLayoutTag = new Nx("com.google.android.material.appbar.AppBarLayout");
                    appBarLayoutTag.a("android", "id", "@+id/_app_bar");
                    aci.inject(appBarLayoutTag, "AppBarLayout");
                    if (f != null) {
                        f.a(toolbarTag);
                        appBarLayoutTag.a(f);
                    } else {
                        appBarLayoutTag.a(toolbarTag);
                    }
                    e.a(appBarLayoutTag);
                    e.a(nx);
                } else {
                    if (e == null) {
                        e = nx;
                    } else {
                        e.a(nx);
                    }
                }
                if (b.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_FAB)) {
                    c(e, c);
                }
                if (c.type == 32) {
                    b(e, c);
                }
                if (b.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_DRAWER)) {
                    Nx drawerLayoutTag = new Nx("androidx.drawerlayout.widget.DrawerLayout");
                    drawerLayoutTag.a("android", "id", "@+id/_drawer");
                    aci.inject(drawerLayoutTag, "DrawerLayout");
                    drawerLayoutTag.a(e);
                    Nx linearLayoutTag = new Nx("LinearLayout");
                    linearLayoutTag.a("android", "id", "@+id/_nav_view");
                    aci.inject(linearLayoutTag, "NavigationDrawer");
                    Nx includeTag = new Nx("include", true);
                    includeTag.a("", "layout", "@layout/_drawer_" + b.fileName);
                    linearLayoutTag.a(includeTag);
                    drawerLayoutTag.a(linearLayoutTag);
                    e = drawerLayoutTag;
                }
            } else {
                e = nx;
            }
        } else {
            e = nx;
        }
        e.a(0, "xmlns", "tools", "http://schemas.android.com/tools");
        e.a(0, "xmlns", "app", "http://schemas.android.com/apk/res-auto");
        e.a(0, "xmlns", "android", "http://schemas.android.com/apk/res/android");
    }

    /**
     * Handles a view's background resource.
     */
    public void a(Nx nx, ViewBean viewBean) {
        String backgroundResource = viewBean.layout.backgroundResource;
        if (backgroundResource == null || "NONE".equalsIgnoreCase(backgroundResource)) {
            int backgroundColor = viewBean.layout.backgroundColor;
            if (backgroundColor != 0xffffff) {
                if (backgroundColor != 0) {
                    int color = backgroundColor & 0xffffff;
                    if (nx.c().equals("BottomAppBar")) {
                        nx.a("app", "backgroundTint", String.format("#%06X", color));
                    } else if (nx.c().equals("CollapsingToolbarLayout")) {
                        nx.a("app", "contentScrim", String.format("#%06X", color));
                    } else {
                        if (!hasAttr("background", viewBean))
                            nx.a("android", "background", String.format("#%06X", color));
                    }
                } else if (nx.c().equals("BottomAppBar")) {
                    nx.a("android", "backgroundTint", "@android:color/transparent");
                } else if (nx.c().equals("CollapsingToolbarLayout")) {
                    nx.a("app", "contentScrim", "?attr/colorPrimary");
                } else {
                    nx.a("android", "background", "@android:color/transparent");
                }
            }
        } else {
            if (!hasAttr("background", viewBean)) {
                boolean isNinePatchBackground = backgroundResource.endsWith(".9");
                nx.a("android", "background", "@drawable/" +
                        (isNinePatchBackground ? backgroundResource.replaceAll("\\.9", "") :
                                backgroundResource));
            }
        }
    }

    public void a(ArrayList<ViewBean> arrayList) {
        a(arrayList, null);
    }

    public void a(ArrayList<ViewBean> arrayList, ViewBean viewBean) {
        c = viewBean;
        d = arrayList;
        a();
    }

    public String b() {
        return e.b();
    }

    public void b(Nx nx, ViewBean viewBean) {
        viewBean.getClassInfo().a();
        String convert = viewBean.convert;

        Nx nx2 = convert.equals("") ? new Nx(viewBean.getClassInfo().a()) :
                new Nx(convert.replaceAll(" ", ""));
        if (convert.equals("include")) {
            nx2.a("", "layout", "@layout/" + viewBean.id);
        } else {
            nx2.a("android", "id", "@+id/" + viewBean.id);
            int type = viewBean.type;
            if (b.fileType == ProjectFileBean.PROJECT_FILE_TYPE_CUSTOM_VIEW) {
                switch (type) {
                    case ViewBean.VIEW_TYPE_WIDGET_TEXTVIEW:
                    case ViewBean.VIEW_TYPE_WIDGET_EDITTEXT:
                    case ViewBean.VIEW_TYPE_WIDGET_IMAGEVIEW:
                    case ViewBean.VIEW_TYPE_WIDGET_PROGRESSBAR:
                    case ViewBean.VIEW_TYPE_WIDGET_CHECKBOX:
                    case ViewBean.VIEW_TYPE_WIDGET_SWITCH:
                    case ViewBean.VIEW_TYPE_WIDGET_SEEKBAR:
                    case ViewBean.VIEW_TYPE_WIDGET_CALENDARVIEW:
                    case 19:
                    case 22:
                    case 23:
                    case 24:
                    case 32:
                        if (!hasAttr("focusable", viewBean))
                            nx2.a("android", "focusable", "false");
                        break;

                    default:
                        break;
                }
            }

            int width = viewBean.layout.width;
            if (width == ViewGroup.LayoutParams.MATCH_PARENT) {
                nx2.a("android", "layout_width", "match_parent");
            } else if (width == ViewGroup.LayoutParams.WRAP_CONTENT) {
                nx2.a("android", "layout_width", "wrap_content");
            } else {
                nx2.a("android", "layout_width", width + "dp");
            }

            int height = viewBean.layout.height;
            if (height == ViewGroup.LayoutParams.MATCH_PARENT) {
                nx2.a("android", "layout_height", "match_parent");
            } else if (height == ViewGroup.LayoutParams.WRAP_CONTENT) {
                nx2.a("android", "layout_height", "wrap_content");
            } else {
                nx2.a("android", "layout_height", height + "dp");
            }
            g(nx2, viewBean);
            i(nx2, viewBean);
            a(nx2, viewBean);
            if (viewBean.getClassInfo().a("ViewGroup")) {
                d(nx2, viewBean);
            }
        }
        a.x.handleWidget(x(viewBean.convert));
        if (viewBean.getClassInfo().b("LinearLayout") && !nx2.c().matches("(BottomAppBar|NavigationView|Coordinator|Floating|Collaps|include)\\w*")) {
            h(nx2, viewBean);
            m(nx2, viewBean);
        }
        if (viewBean.getClassInfo().a("TextView")) {
            d(nx2, viewBean);
            j(nx2, viewBean);
        }
        if (viewBean.getClassInfo().a("ImageView")) {
            e(nx2, viewBean);
            if (!nx2.b().contains(".")) {
                ea(nx2, viewBean);
            }
        }
        if (viewBean.getClassInfo().b("SeekBar")) {
            d(nx2, viewBean);
        }
        if (viewBean.getClassInfo().b("ProgressBar")) {
            d(nx2, viewBean);
        }
        if (viewBean.getClassInfo().b("WaveSideBar")) {
            o(nx2, viewBean);
        }
        k(nx2, viewBean);
        int i4 = viewBean.parentType;
        if (!viewBean.convert.equals("include")) {
            if (i4 == 0) {
                f(nx2, viewBean);
                l(nx2, viewBean);
            } else if (i4 == 2 || i4 == 12) {
                f(nx2, viewBean);
            }
        }
        if (viewBean.getClassInfo().a("ViewGroup")) {
            for (ViewBean bean : d) {
                if (bean.parent != null && bean.parent.equals(viewBean.id)) {
                    b(nx2, bean);
                }
            }
        }
        if (!viewBean.inject.equals("")) {
            nx2.b(viewBean.inject.replaceAll(" ", ""));
        }
        if (nx2.c().equals("CollapsingToolbarLayout")) {
            f = nx2;
        } else {
            nx.a(nx2);
        }
    }

    /**
     * Adds a FAB.
     */
    public void c(Nx nx, ViewBean viewBean) {
        Nx floatingActionButtonTag = new Nx("com.google.android.material.floatingactionbutton.FloatingActionButton");
        floatingActionButtonTag.a("android", "id", "@+id/" + viewBean.id);
        floatingActionButtonTag.a("android", "layout_width", "wrap_content");
        floatingActionButtonTag.a("android", "layout_height", "wrap_content");
        g(floatingActionButtonTag, viewBean);
        f(floatingActionButtonTag, viewBean);

        String resName = viewBean.image.resName;
        if (resName != null && resName.length() > 0 && !resName.equals("NONE")) {
            floatingActionButtonTag.a("app", "srcCompat", "@drawable/" + resName.toLowerCase());
        }
        if (viewBean.id.equals("_fab")) {
            aci.inject(floatingActionButtonTag, "FloatingActionButton");
        }
        k(floatingActionButtonTag, viewBean);
        nx.a(floatingActionButtonTag);
    }

    /**
     * Handles a view's <code>android:gravity</code> property.
     */
    public void d(Nx nx, ViewBean viewBean) {
        int gravity = viewBean.layout.gravity;
        if (gravity != 0) {
            String str = "";
            int i2 = gravity & Gravity.FILL_VERTICAL;
            int i3 = gravity & Gravity.FILL_HORIZONTAL;
            if (i3 == 1) {
                str = "center_horizontal";
            } else {
                if ((i3 & 3) == 3) {
                    str = "left";
                }
                if ((i3 & 5) == 5) {
                    if (str.length() > 0) {
                        str = str + "|";
                    }
                    str = str + "right";
                }
            }
            if (i2 == 16) {
                if (str.length() > 0) {
                    str = str + "|";
                }
                str = str + "center_vertical";
            } else {
                if ((i2 & 48) == 48) {
                    if (str.length() > 0) {
                        str = str + "|";
                    }
                    str = str + "top";
                }
                if ((i2 & 80) == 80) {
                    if (str.length() > 0) {
                        str = str + "|";
                    }
                    str = str + "bottom";
                }
            }
            nx.a("android", "gravity", str);
        }
    }

    /**
     * Handles an image container's image, the property can either be <code>android:src</code>
     * or <code>app:srcCompat</code>.
     */
    public void e(Nx nx, ViewBean viewBean) {
        String resName = viewBean.image.resName;
        if (resName.length() > 0 && !"NONE".equals(resName)) {
            String value = "@drawable/" + resName.toLowerCase();
            if (nx.c().equals("FloatingActionButton")) {
                nx.a("app", "srcCompat", value);
            } else {
                nx.a("android", "src", value);
            }
        }
    }

    /**
     * Handles an ImageView's scaleType.
     *
     * @see ImageView.ScaleType
     */
    public void ea(Nx nx, ViewBean viewBean) {
        if (viewBean.image.scaleType.equals(ImageBean.SCALE_TYPE_CENTER)) {
            nx.a("android", "scaleType", "center");
        } else if (viewBean.image.scaleType.equals(ImageBean.SCALE_TYPE_FIT_XY)) {
            nx.a("android", "scaleType", "fitXY");
        } else if (viewBean.image.scaleType.equals(ImageBean.SCALE_TYPE_FIT_START)) {
            nx.a("android", "scaleType", "fitStart");
        } else if (viewBean.image.scaleType.equals(ImageBean.SCALE_TYPE_FIT_END)) {
            nx.a("android", "scaleType", "fitEnd");
        } else if (viewBean.image.scaleType.equals(ImageBean.SCALE_TYPE_FIT_CENTER)) {
            nx.a("android", "scaleType", "fitCenter");
        } else if (viewBean.image.scaleType.equals(ImageBean.SCALE_TYPE_CENTER_CROP)) {
            nx.a("android", "scaleType", "centerCrop");
        } else if (viewBean.image.scaleType.equals(ImageBean.SCALE_TYPE_CENTER_INSIDE)) {
            nx.a("android", "scaleType", "centerInside");
        }
    }

    /**
     * Handles a view's layout gravity.
     *
     * @see Gravity
     */
    public void f(Nx nx, ViewBean viewBean) {
        int i = viewBean.layout.layoutGravity;
        if (i != 0) {
            String str = "";
            int i2 = i & 112;
            int i3 = i & 7;
            if (i3 == 1) {
                str = "center_horizontal";
            } else {
                if ((i3 & 3) == 3) {
                    str = "left";
                }
                if ((i3 & 5) == 5) {
                    if (str.length() > 0) {
                        str = str + "|";
                    }
                    str = str + "right";
                }
            }
            if (i2 == 16) {
                if (str.length() > 0) {
                    str = str + "|";
                }
                str = str + "center_vertical";
            } else {
                if ((i2 & 48) == 48) {
                    if (str.length() > 0) {
                        str = str + "|";
                    }
                    str = str + "top";
                }
                if ((i2 & 80) == 80) {
                    if (str.length() > 0) {
                        str = str + "|";
                    }
                    str = str + "bottom";
                }
            }
            nx.a("android", "layout_gravity", str);
        }
    }

    /**
     * Handles a view's layout margin.
     *
     * @see ViewGroup.MarginLayoutParams
     */
    public void g(Nx nx, ViewBean viewBean) {
        LayoutBean layoutBean = viewBean.layout;
        int marginLeft = layoutBean.marginLeft;
        int marginTop = layoutBean.marginTop;
        int marginRight = layoutBean.marginRight;
        int marginBottom = layoutBean.marginBottom;

        if (marginLeft == marginRight && marginTop == marginBottom
                && marginLeft == marginTop && marginLeft > 0) {
            nx.a("android", "layout_margin", marginLeft + "dp");
            return;
        }
        if (marginLeft > 0) {
            nx.a("android", "layout_marginLeft", marginLeft + "dp");
        }
        if (viewBean.layout.marginTop > 0) {
            nx.a("android", "layout_marginTop", viewBean.layout.marginTop + "dp");
        }
        if (marginRight > 0) {
            nx.a("android", "layout_marginRight", marginRight + "dp");
        }
        if (marginBottom > 0) {
            nx.a("android", "layout_marginBottom", marginBottom + "dp");
        }
    }

    /**
     * Handles a view's orientation.
     *
     * @see LinearLayout#getOrientation()
     */
    public void h(Nx nx, ViewBean viewBean) {
        int orientation = viewBean.layout.orientation;
        if (orientation == LinearLayout.HORIZONTAL) {
            nx.a("android", "orientation", "horizontal");
        } else if (orientation == LinearLayout.VERTICAL) {
            nx.a("android", "orientation", "vertical");
        }
    }

    /**
     * Handles a view's padding.
     *
     * @see View#getPaddingLeft()
     * @see View#getPaddingTop()
     * @see View#getPaddingRight()
     * @see View#getPaddingBottom()
     */
    public void i(Nx nx, ViewBean viewBean) {
        LayoutBean layoutBean = viewBean.layout;
        int paddingLeft = layoutBean.paddingLeft;
        int paddingTop = layoutBean.paddingTop;
        int paddingRight = layoutBean.paddingRight;
        int paddingBottom = layoutBean.paddingBottom;

        if (paddingLeft == paddingRight && paddingTop == paddingBottom
                && paddingLeft == paddingTop && paddingLeft > 0) {
            nx.a("android", "padding", paddingLeft + "dp");
            return;
        }
        if (paddingLeft > 0) {
            nx.a("android", "paddingLeft", paddingLeft + "dp");
        }
        if (paddingTop > 0) {
            nx.a("android", "paddingTop", paddingTop + "dp");
        }
        if (paddingRight > 0) {
            nx.a("android", "paddingRight", paddingRight + "dp");
        }
        if (paddingBottom > 0) {
            nx.a("android", "paddingBottom", paddingBottom + "dp");
        }
    }

    /**
     * Handles properties for text-related views, such as <code>android:text</code>,
     * <code>android:textSize</code> and <code>android:textStyle</code>.
     */
    public void j(Nx nx, ViewBean viewBean) {
        String text = viewBean.text.text;
        if (text != null && text.length() > 0) {
            nx.a("android", "text", a(text));
        }

        int textSize = viewBean.text.textSize;
        if (textSize > 0) {
            nx.a("android", "textSize", textSize + "sp");
        }
        int textType = viewBean.text.textType;
        if (textType == TextBean.TEXT_TYPE_BOLD) {
            nx.a("android", "textStyle", "bold");
        } else if (textType == TextBean.TEXT_TYPE_ITALIC) {
            nx.a("android", "textStyle", "italic");
        } else if (textType == TextBean.TEXT_TYPE_BOLDITALIC) {
            nx.a("android", "textStyle", "bold|italic");
        }
        if (viewBean.text.textColor != 0) {
            if (!hasAttr("textColor", viewBean))
                nx.a("android", "textColor", String.format("#%06X", viewBean.text.textColor & 0xffffff));
        }
        switch (viewBean.type) {
            case ViewBean.VIEW_TYPE_WIDGET_EDITTEXT:
            case 23:
            case 24:
                String hint = viewBean.text.hint;
                if (hint != null && hint.length() > 0) {
                    nx.a("android", "hint", a(hint));
                }
                if (viewBean.text.hintColor != 0) {
                    if (!hasAttr("textColorHint", viewBean))
                        nx.a("android", "textColorHint", String.format("#%06X", viewBean.text.hintColor & 0xffffff));
                }
                if (viewBean.text.singleLine != 0) {
                    nx.a("android", "singleLine", "true");
                }

                int line = viewBean.text.line;
                if (line > 0) {
                    nx.a("android", "lines", String.valueOf(line));
                }

                int inputType = viewBean.text.inputType;
                if (inputType != TextBean.INPUT_TYPE_TEXT) {
                    nx.a("android", "inputType", sq.a("property_input_type", inputType));
                }

                int imeOption = viewBean.text.imeOption;
                if (imeOption != TextBean.IME_OPTION_NORMAL) {
                    if (imeOption == TextBean.IME_OPTION_NONE) {
                        nx.a("android", "imeOptions", "actionNone");
                    } else if (imeOption == TextBean.IME_OPTION_GO) {
                        nx.a("android", "imeOptions", "actionGo");
                    } else if (imeOption == TextBean.IME_OPTION_SEARCH) {
                        nx.a("android", "imeOptions", "actionSearch");
                    } else if (imeOption == TextBean.IME_OPTION_SEND) {
                        nx.a("android", "imeOptions", "actionSend");
                    } else if (imeOption == TextBean.IME_OPTION_NEXT) {
                        nx.a("android", "imeOptions", "actionNext");
                    } else if (imeOption == TextBean.IME_OPTION_DONE) {
                        nx.a("android", "imeOptions", "actionDone");
                    }
                }
                break;

            case ViewBean.VIEW_TYPE_WIDGET_TEXTVIEW:
                if (viewBean.text.singleLine != 0) {
                    nx.a("android", "singleLine", "true");
                }
                line = viewBean.text.line;
                if (line > 0) {
                    nx.a("android", "lines", String.valueOf(line));
                }
                break;
        }
    }

    public void k(Nx nx, ViewBean viewBean) {
        if (viewBean.enabled == 0) {
            nx.a("android", "enabled", "false");
        }
        if (viewBean.clickable == 0) {
            nx.a("android", "clickable", "false");
        }
        int rotate = viewBean.image.rotate;
        if (rotate != 0) {
            nx.a("android", "rotation", String.valueOf(rotate));
        }
        float alpha = viewBean.alpha;
        if (1.0f != alpha) {
            nx.a("android", "alpha", String.valueOf(alpha));
        }
        if (0.0f != viewBean.translationX) {
            nx.a("android", "translationX", viewBean.translationX + "dp");
        }
        if (0.0f != viewBean.translationY) {
            nx.a("android", "translationY", viewBean.translationY + "dp");
        }
        float scaleX = viewBean.scaleX;
        if (1.0f != scaleX) {
            nx.a("android", "scaleX", String.valueOf(scaleX));
        }
        float scaleY = viewBean.scaleY;
        if (1.0f != scaleY) {
            nx.a("android", "scaleY", String.valueOf(scaleY));
        }

        switch (viewBean.type) {
            case ViewBean.VIEW_TYPE_WIDGET_CHECKBOX:
            case ViewBean.VIEW_TYPE_WIDGET_SWITCH:
                if (viewBean.checked == 1) {
                    nx.a("android", "checked", "true");
                }
                break;

            case ViewBean.VIEW_TYPE_WIDGET_SEEKBAR:
                int progress = viewBean.progress;
                if (progress > ViewBean.DEFAULT_PROGRESS) {
                    nx.a("android", "progress", String.valueOf(progress));
                }

                int max = viewBean.max;
                if (max != ViewBean.DEFAULT_MAX) {
                    nx.a("android", "max", String.valueOf(max));
                }
                break;

            case ViewBean.VIEW_TYPE_WIDGET_CALENDARVIEW:
                int firstDayOfWeek = viewBean.firstDayOfWeek;
                if (firstDayOfWeek != 1) {
                    nx.a("android", "firstDayOfWeek", String.valueOf(firstDayOfWeek));
                }
                break;

            case ViewBean.VIEW_TYPE_WIDGET_SPINNER:
                int spinnerMode = viewBean.spinnerMode;
                if (spinnerMode == ViewBean.SPINNER_MODE_DROPDOWN) {
                    nx.a("android", "spinnerMode", "dropdown");
                } else if (spinnerMode == ViewBean.SPINNER_MODE_DIALOG) {
                    nx.a("android", "spinnerMode", "dialog");
                }
                break;

            case ViewBean.VIEW_TYPE_WIDGET_LISTVIEW:
                int dividerHeight = viewBean.dividerHeight;
                if (dividerHeight != 1) {
                    nx.a("android", "dividerHeight", dividerHeight + "dp");
                }
                if (dividerHeight == 0) {
                    nx.a("android", "divider", "@null");
                }

                switch (viewBean.choiceMode) {
                    case ViewBean.CHOICE_MODE_NONE:
                        nx.a("android", "choiceMode", "none");
                        break;

                    case ViewBean.CHOICE_MODE_SINGLE:
                        nx.a("android", "choiceMode", "singleChoice");
                        break;

                    case ViewBean.CHOICE_MODE_MULTI:
                        nx.a("android", "choiceMode", "multipleChoice");
                        break;
                }
                break;

            case ViewBean.VIEW_TYPE_WIDGET_ADVIEW:
                String adSize = viewBean.adSize;
                if (adSize == null || adSize.length() <= 0) {
                    nx.a("app", "adSize", "SMART_BANNER");
                } else {
                    nx.a("app", "adSize", adSize);
                }

                if (a.f) {
                    nx.a("app", "adUnitId", a.r);
                } else {
                    nx.a("app", "adUnitId", "ca-app-pub-3940256099942544/6300978111");
                }
                break;

            case ViewBean.VIEW_TYPE_WIDGET_PROGRESSBAR:
                progress = viewBean.progress;
                if (progress > ViewBean.DEFAULT_PROGRESS) {
                    nx.a("android", "progress", String.valueOf(progress));
                }

                max = viewBean.max;
                if (max != ViewBean.DEFAULT_MAX) {
                    nx.a("android", "max", String.valueOf(max));
                }

                String indeterminate = viewBean.indeterminate;
                if (indeterminate != null && indeterminate.length() > 0) {
                    nx.a("android", "indeterminate", indeterminate);
                }
                String progressStyle = viewBean.progressStyle;
                if (progressStyle != null && progressStyle.length() > 0) {
                    nx.a(null, "style", progressStyle);
                }
                break;
        }
    }

    /**
     * Handles a view's <code>android:layout_weight</code> property.
     */
    public void l(Nx nx, ViewBean viewBean) {
        int weight = viewBean.layout.weight;
        if (weight > 0) {
            nx.a("android", "layout_weight", String.valueOf(weight));
        }
    }

    /**
     * Handles a view's <code>android:weightSum</code> property.
     */
    public void m(Nx nx, ViewBean viewBean) {
        int weightSum = viewBean.layout.weightSum;
        if (weightSum > 0) {
            nx.a("android", "weightSum", String.valueOf(weightSum));
        }
    }

    public void n(Nx nx, ViewBean viewBean) {
        nx.a("app", "tabGravity", "fill");
        nx.a("app", "tabMode", "fixed");
        nx.a("app", "tabIndicatorHeight", "3dp");
        nx.a("app", "tabIndicatorColor", "@android:color/white");
        nx.a("app", "tabSelectedTextColor", "@android:color/white");
        nx.a("app", "tabTextColor", "@android:color/white");
        nx.a("app", "tabTextAppearance", "@android:style/TextAppearance.Widget.TabWidget");
    }

    /**
     * check whether the atrribute (attrName) is injected to the ViewBean or not.
     */
    public boolean hasAttr(String attrName, ViewBean bean) {
        final String inject = bean.inject;
        if (inject == null || inject.equals("")) return false;
        return Pattern.compile("(android|app) *?: *?" + attrName).matcher(inject).find();
    }

    public void o(Nx nx, ViewBean viewBean) {
        int textSize = viewBean.text.textSize;
        if (textSize > 0) {
            nx.a("app", "sidebar_text_size", textSize + "sp");
        }

        int textColor = viewBean.text.textColor;
        if (textColor != 0) {
            nx.a("app", "sidebar_text_color", String.format("#%06X", textColor & 0xffffff));
        }
    }
}
