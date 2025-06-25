package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import models.NewsBlock;
import models.NewsDetail;
import com.example.greenerieplantie.R;

public class ListNewsDetail {
//
//    public static List<NewsDetail> getSampleNews() {
//        List<NewsDetail> newsList = new ArrayList<>();
//
//        newsList.add(new NewsDetail(
//                "Comprehensive Guide to Seedling Propagation for Beginners",
//                "An Pham",
//                "18 May 2025",
//                Arrays.asList(
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "When starting gardening, what should you pay attention to and prepare? What are the steps to transplant a plant from a pot or propagate from a bulb or seedling?"),
//                        new NewsBlock(NewsBlock.Type.HEADING, "Things to Do Before Planting"),
//                        new NewsBlock(NewsBlock.Type.HEADING, "Check the Soil Type Before You Start"),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "Each plant is suitable for a different type of soil. Therefore, testing the soil's characteristics is essential for choosing the right plants and planning future soil improvements in your garden..."),
//                        new NewsBlock(NewsBlock.Type.IMAGE, "img_blog1_1"),
//                        new NewsBlock(NewsBlock.Type.HEADING, "Read the Care Instructions for the Plant You Want to Grow"),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "Take time to read about the plant’s care techniques, when to start planting, how to prune, and fertilize before diving into the actual work..."),
//                        new NewsBlock(NewsBlock.Type.IMAGE, "img_blog1_2"),
//                        new NewsBlock(NewsBlock.Type.HEADING, "Instructions for Transplanting and Planting"),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "Small shrubs and flowers are often pre-grown in pots. To replant them..."),
//                        new NewsBlock(NewsBlock.Type.IMAGE, "img_blog1_3")
//                ),
//                R.drawable.img_blog1_01
//        ));
//
//        newsList.add(new NewsDetail(
//                "How to Compost Household Waste into Odor-Free Fertilizer",
//                "Urban Agriculture",
//                "25 May 2025",
//                Arrays.asList(
//                        new NewsBlock(NewsBlock.Type.HEADING, "What Materials Are Used for Organic Composting?"),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "Organic compost is essential and truly beneficial for your garden. Instead of buying ready-made compost products on the market, you can make your own high-quality compost at home using daily kitchen waste. This not only creates a valuable product but also helps protect the environment."),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "To compost effectively and quickly, you should mix nitrogen-rich and carbon-rich materials in a 50:50 ratio. Compost bins that contain too much green material may emit foul odors, while bins with too much carbon will decompose very slowly. If you cannot balance the optimal ratio, consider adding microbial enzymes and paying attention to the compost bin's structure to accelerate the composting process."),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "Nitrogen-rich materials: freshly cut grass, annual weeds, fruit and vegetable peels, vegetable stems, used tea bags\nCarbon-rich materials: pruned branches, newspapers, cardboard, sawdust, straw, paper bags, eggshells\nMaterials to avoid adding to the compost pile: diseased plants, cooked food, perennial weeds, citrus fruits (high acidity, slow to decompose, and harmful to earthworms), raw meat, dairy products, plastic-coated paper, colored printed paper, charcoal, pet waste (can carry harmful pathogens)"),
//                        new NewsBlock(NewsBlock.Type.HEADING, "Steps to Composting"),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "How should you go about composting, and what should be added to produce high-quality compost without stinking up your home? Let’s find out below with Urban Agriculture."),
//                        new NewsBlock(NewsBlock.Type.HEADING, "Step 1"),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "If composting in the garden, bury the compost bin directly in the ground. This allows liquid from the bin to seep into the soil and enables earthworms and beneficial microbes to access the compost, helping the process go faster. If you're composting on a terrace or balcony, use a two-layer compost bin. The top layer holds the composting materials, while the bottom layer collects the liquid and has a valve or tap for drainage. A fine mesh separates the two layers, allowing the compost leachate to flow down easily. This liquid is an excellent organic fertilizer for plants."),
//                        new NewsBlock(NewsBlock.Type.HEADING, "Step 2"),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "During the composting process, stir the pile occasionally to mix the materials evenly, helping newly added materials decompose faster. Always keep the bin covered to prevent rain from soaking the compost."),
//                        new NewsBlock(NewsBlock.Type.HEADING, "Step 3"),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "When the compost turns brown or black, becomes crumbly, and has little to no odor, it means the process is complete. You can now use it as fertilizer in your garden."),
//                        new NewsBlock(NewsBlock.Type.HEADING, "Additives to Accelerate Composting and Eliminate Odor"),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "Some microbial products, beneficial fungi, or red worms (Eisenia fetida) are often added during composting. These are crucial elements that help break down waste quickly and odourless."),
//                        new NewsBlock(NewsBlock.Type.HEADING, "Trichoderma"),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "Trichoderma is a beneficial fungus that has long been used in agriculture. It helps decompose organic matter and fibers, eliminates harmful pathogens, and supports healthy plant growth."),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "Many commercial Trichoderma products are available today. The quality depends on the density of fungal spores, usually indicated by the abbreviation CFU (Colony Forming Units). A standard quality product typically has a density of 10⁸ CFU. Products with lower density are less effective. Some high-end products have densities of 10⁹ CFU, but they are rare due to the complex production technology. With these, only a small amount is needed for effective results."),
//                        new NewsBlock(NewsBlock.Type.HEADING, "EM (Effective Microorganisms)"),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "EM is commonly used to compost organic waste or make herbal bio-pesticides. EM has excellent deodorizing capabilities. Within one hour of spraying EM on a chicken manure pile or animal pen, the bad odor is nearly gone."),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "EM is available in powder and liquid form. Because it has a short shelf life, product quality depends on how it is stored and the producer. For effective composting, buy EM from reputable sources. Primary EM or EM mother cultures are stronger and last longer than regular types."),
//                        new NewsBlock(NewsBlock.Type.HEADING, "Red Worms (Eisenia fetida)"),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "Red worms are highly effective \"workers\" for processing organic waste. You can purchase them from worm farms and place them into your compost pile or household compost bin. They will naturally consume the food waste and produce worm castings – one of the most nutrient-rich organic fertilizers available. However, avoid feeding them citrus peels, spicy food, or placing them in overly hot environments. These conditions can cause the worms to stop developing, die, or leave the bin altogether.")
//                ),
//        R.drawable.img_blog2_01
//        ));
//
//        newsList.add(new NewsDetail(
//                "Basic Agricultural Terms",
//                "Green School",
//                "28 May 2025",
//                Arrays.asList(
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "Like any other profession, agriculture has its own specialized terminology. Understanding these terms helps gardeners quickly absorb technical guidance, making gardening easier and more enjoyable. To explore common and basic gardening terms, let’s dive into the article below."),
//
//                        new NewsBlock(NewsBlock.Type.HEADING, "Hardening Off Seedlings"),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "Typically, seedlings are sown, propagated, or cut and grown in a separate nursery area. During this stage, they are provided with optimal conditions of moisture, temperature, light, and nutrients to develop. After a period of care in the nursery, the seedlings are transferred to fields or large gardens for planting. Due to differences in environmental conditions, if the seedlings are moved directly from the nursery, there is a high risk of them dying due to the sudden change. Therefore, nurseries usually have a \"hardening off\" area, which helps the plants acclimate to outdoor conditions. This process makes the plants sturdier and healthier before being transplanted."),
//
//                        new NewsBlock(NewsBlock.Type.HEADING, "Soil pH"),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "Different plants thrive in different soil pH levels. pH indicates whether the soil is acidic, alkaline, or neutral. Most plants prefer a neutral pH level, between 6.5 and 7. Some, like pineapples, prefer acidic soil (pH < 6.5) to grow well."),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "Determining your garden's soil pH is simple. Just buy pH test strips and take soil samples from your garden. Mix the samples together, soak them in clean water for about 30 minutes, let the soil settle, then dip the test strip into the water. Compare the color to the chart provided to get your result."),
//
//                        new NewsBlock(NewsBlock.Type.HEADING, "Loosening and Breaking the Soil Crust"),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "This involves using a rake, hoe, shovel, or any tool to gently loosen the soil around the plant base. It is often done after heavy rains, especially in clayey or low-organic soils. After rain or heavy watering, the soil’s loose surface structure is destroyed, forming a crust that covers the plant's roots. This crust prevents oxygen from reaching the roots. If not addressed in time, the plant may quickly yellow and lose its leaves due to oxygen deficiency."),
//
//                        new NewsBlock(NewsBlock.Type.HEADING, "Bare-Root Plants"),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "These are plants that have been removed from the ground with no soil attached to their roots. This method is often used to preserve plants in regions with freezing winters. Additionally, bare-root plants are convenient for exporting ornamental plants to other countries."),
//
//                        new NewsBlock(NewsBlock.Type.HEADING, "Thiên địch (côn trùng có lợi)"),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "These are species beneficial to agricultural cultivation. Beneficial insects prey on harmful pests or compete with them for food, thus suppressing pest outbreaks. Common natural enemies include ladybugs, praying mantises, and bees."),
//
//                        new NewsBlock(NewsBlock.Type.HEADING, "Annual Plants"),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "Plants that complete their life cycle from planting to flowering and seed production within one year."),
//
//                        new NewsBlock(NewsBlock.Type.HEADING, "Biennial Plants"),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "Plants that take two years to complete their life cycle, flowering and producing seeds in the second year."),
//
//                        new NewsBlock(NewsBlock.Type.HEADING, "Biological Control"),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "The use of living microorganisms to control pests. For example, antagonistic fungi can be used to control harmful fungi, and ladybugs are used to manage aphids. This principle is commonly applied in organic farming."),
//
//                        new NewsBlock(NewsBlock.Type.HEADING, "Intercropping"),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "This involves planting crops in the spaces between other plants before the area becomes fully occupied. Common examples include green onions, radishes, and lettuce.")
//                ),
//        R.drawable.img_blog3_01
//        ));
//
//        newsList.add(new NewsDetail(
//                "How to Compost Household Waste into Odor-Free Fertilizer",
//                "Urban Agriculture",
//                "25 May 2025",
//                Arrays.asList(
//                        new NewsBlock(NewsBlock.Type.HEADING, "What Materials Are Used for Organic Composting?"),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "Organic compost is essential and truly beneficial for your garden. Instead of buying ready-made compost products on the market, you can make your own high-quality compost at home using daily kitchen waste. This not only creates a valuable product but also helps protect the environment."),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "To compost effectively and quickly, mix nitrogen-rich and carbon-rich materials in a 50:50 ratio. Too much green material causes foul odors; too much carbon slows decomposition. Consider microbial enzymes and bin structure to speed up the process."),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "Nitrogen-rich: fresh grass, annual weeds, fruit/veggie peels, veggie stems, used tea bags."),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "Carbon-rich: branches, newspaper, cardboard, sawdust, straw, paper bags, eggshells."),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "Avoid: diseased plants, cooked food, perennial weeds, citrus fruits, raw meat, dairy, plastic, colored paper, charcoal, pet waste."),
//
//                        new NewsBlock(NewsBlock.Type.HEADING, "Steps to Composting"),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "Step 1: Garden compost bins should be buried in the ground for faster decomposition. Balcony composting needs a 2-layer bin with drainage and separation mesh."),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "Step 2: Stir the pile occasionally to speed up decomposition. Keep the bin covered."),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "Step 3: Compost is ready when dark, crumbly, and odor-free."),
//
//                        new NewsBlock(NewsBlock.Type.HEADING, "Additives to Accelerate Composting and Eliminate Odor"),
//                        new NewsBlock(NewsBlock.Type.HEADING, "Trichoderma"),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "Trichoderma is a beneficial fungus used in agriculture to break down organic matter and eliminate pathogens. Choose products with high CFU (10^8 or higher) for effectiveness."),
//
//                        new NewsBlock(NewsBlock.Type.HEADING, "EM (Effective Microorganisms)"),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "EM deodorizes and accelerates composting. Available in powder or liquid, EM works best when fresh and from reliable sources."),
//
//                        new NewsBlock(NewsBlock.Type.HEADING, "Red Worms (Eisenia fetida)"),
//                        new NewsBlock(NewsBlock.Type.PARAGRAPH, "Red worms digest waste and produce rich fertilizer. Avoid spicy food, citrus, or hot conditions.")
//                ),
//        R.drawable.img_blog4_01
//        ));
//
//        return newsList;
//    }
}
